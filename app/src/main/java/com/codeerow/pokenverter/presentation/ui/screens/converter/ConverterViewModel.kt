package com.codeerow.pokenverter.presentation.ui.screens.converter

import androidx.lifecycle.MutableLiveData
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.spirit.mvvm.viewmodel.RxViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Input
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Output


class ConverterViewModel(
    private val observeCurrencies: UseCase<Input, Observable<Output>>
) : RxViewModel() {

    val anchor: BehaviorRelay<Pair<String?, BigDecimal>> =
        BehaviorRelay.createDefault(null to BigDecimal.ZERO)

    val currencies = MutableLiveData<List<Pair<String, BigDecimal>>>(listOf())

    init {
        anchor.switchMap { anchor ->
            observeCurrencies(Input(anchor, currencies.value ?: listOf()))
                .map(Output::currencies)
                .doOnNext(currencies::postValue)
        }.subscribeOn(Schedulers.io()).subscribeByViewModel()
    }
}
