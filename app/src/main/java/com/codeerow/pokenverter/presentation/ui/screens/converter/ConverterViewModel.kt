package com.codeerow.pokenverter.presentation.ui.screens.converter

import androidx.lifecycle.MutableLiveData
import com.codeerow.pokenverter.domain.usecases.impl.FetchCurrencies
import com.codeerow.pokenverter.domain.usecases.impl.FetchCurrencies.Input
import com.codeerow.spirit.mvvm.viewmodel.RxViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class ConverterViewModel(
    private val fetchCurrencies: FetchCurrencies,
    private val retryPolicy: (Throwable) -> Observable<*>
) : RxViewModel() {

    companion object {
        val DEFAULT_ANCHOR = null to BigDecimal.ZERO

        private val timer = Observable.interval(
            0L, 1L, TimeUnit.SECONDS
        )
    }

    val anchor: BehaviorRelay<Pair<String?, BigDecimal>> =
        BehaviorRelay.createDefault(DEFAULT_ANCHOR)

    val currencies = MutableLiveData<FetchCurrencies.Output>()


    init {
        anchor.switchMap { anchor ->
            timer.map {
                val recentCurrencies =
                    (currencies.value as? FetchCurrencies.Output.Success)?.currencies ?: listOf()
                Input(anchor, recentCurrencies)
            }.flatMap { input -> fetchCurrencies(input) }
        }
            .retryWhen { it.flatMap(retryPolicy) }
            .subscribeOn(Schedulers.io())
            .subscribe(currencies::postValue)
            .bindToLifecycle()
    }
}
