package com.codeerow.currenciesapp.presentation.ui.screens.converter

import androidx.lifecycle.MutableLiveData
import com.codeerow.currenciesapp.domain.usecases.ObserveRatesUseCase
import com.codeerow.spirit.mvvm.viewmodel.RxViewModel
import io.reactivex.Completable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal


class ConverterViewModel(private val observeRatesUseCase: ObserveRatesUseCase) : RxViewModel() {

    val rates = MutableLiveData<List<Pair<String, BigDecimal>>>(listOf())

    private var disposable = Disposables.empty()

    init {
        subscribeForRates(null)
    }

    private fun subscribeForRates(anchorCurrency: String?) {
        disposable.dispose()
        disposable = Completable.fromRunnable {
            if (anchorCurrency != null) {
                rates.value?.toMutableList()?.let { newList ->
                    val anchor = newList.find { it.first == anchorCurrency } ?: TODO()
                    newList.remove(anchor)
                    newList.add(0, anchor)
                    rates.postValue(newList)
                }
            }
        }.andThen(observeRatesUseCase.execute(ObserveRatesUseCase.Request(anchorCurrency)))
            .doOnNext { rates ->
                this.rates.postValue(if (this.rates.value?.isEmpty() == true) rates.toList()
                else {
                    this.rates.value?.map { it.first to rates.getOrElse(it.first) { BigDecimal("0.0") } }
                })
            }
            .subscribeOn(Schedulers.io())
            .subscribeByViewModel()
    }

    fun setAnchor(rate: Pair<String, BigDecimal>) {
        subscribeForRates(rate.first)
    }
}
