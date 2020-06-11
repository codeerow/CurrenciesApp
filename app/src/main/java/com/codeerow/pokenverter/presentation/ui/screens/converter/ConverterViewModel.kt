package com.codeerow.pokenverter.presentation.ui.screens.converter

import androidx.lifecycle.MutableLiveData
import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.data.model.ApplicationException
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Input
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Output
import com.codeerow.spirit.mvvm.viewmodel.RxViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal


class ConverterViewModel(
    private val observeCurrencies: UseCase<Input, Observable<Output>>
) : RxViewModel() {

    companion object {
        val DEFAULT_ANCHOR = null to BigDecimal.ZERO
    }

    val anchor: BehaviorRelay<Pair<String?, BigDecimal>> =
        BehaviorRelay.createDefault(DEFAULT_ANCHOR)

    val currencies = MutableLiveData<List<Pair<String, BigDecimal>>>()
    val errorRes = MutableLiveData<Int>()

    init {
        anchor.switchMap { anchor ->
            val input = Input(anchor, currencies.value ?: listOf())
            observeCurrencies(input).map(Output::currencies)
        }
            .subscribeOn(Schedulers.io())
            .subscribe(currencies::postValue, ::handleException)
            .bindToLifecycle()
    }

    private fun handleException(throwable: Throwable) = with(throwable) {
        if (this is ApplicationException) errorRes.postValue(messageRes)
        else errorRes.postValue(R.string.application_error_default_message)
    }
}
