package com.codeerow.currenciesapp.presentation.ui.screens.converter

import androidx.lifecycle.MutableLiveData
import com.codeerow.currenciesapp.domain.usecases.FetchRatesUseCase
import com.codeerow.currenciesapp.presentation.model.Currency
import com.codeerow.spirit.mvvm.viewmodel.RxViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit


class ConverterViewModel(fetchRatesUseCase: FetchRatesUseCase) : RxViewModel() {

    val rates = MutableLiveData<List<Pair<Currency, Double>>>()


    private val anchorCurrency: BehaviorRelay<Optional<Currency>> =
        BehaviorRelay.createDefault(Optional.empty())
    private val anchorValue: BehaviorRelay<Optional<Double>> =
        BehaviorRelay.createDefault(Optional.empty())

    init {
        Observable.combineLatest(
            Observable.interval(1, TimeUnit.SECONDS),
            anchorCurrency,
            anchorValue,
            Function3<Long, Optional<Currency>, Optional<Double>, Pair<Currency?, Double?>>
            { _, t2, t3 ->
                Pair(
                    if (t2.isPresent) t2.get() else null,
                    if (t3.isPresent) t3.get() else null
                )
            }
        ).switchMapSingle { (currency, value) ->
            fetchRatesUseCase.execute(
                FetchRatesUseCase.Request(currency?.id, value)
            )
        }
            .doOnNext(rates::postValue)
            .subscribeOn(Schedulers.io())
            .subscribeByViewModel()
    }


    fun setAnchor(currency: Currency) {
        anchorCurrency.accept(Optional.of(currency))
    }

    fun setValue(value: Double) {
        anchorValue.accept(Optional.of(value))
    }
}
