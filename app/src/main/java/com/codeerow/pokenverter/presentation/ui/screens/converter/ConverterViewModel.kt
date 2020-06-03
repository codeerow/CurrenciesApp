package com.codeerow.pokenverter.presentation.ui.screens.converter

import androidx.lifecycle.MutableLiveData
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.FetchCurrenciesUseCase
import com.codeerow.spirit.mvvm.viewmodel.RxViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class ConverterViewModel(
    private val fetchRatesUseCase: UseCase<FetchCurrenciesUseCase.Input, Single<FetchCurrenciesUseCase.Output>>,
    fetchRatesRetryPolicy: (Observable<Throwable>) -> Observable<*>
) : RxViewModel() {

    companion object {
        private const val INITIAL_DELAY = 0L
        private const val INTERVAL_PERIOD = 1L
        private val INTERVAL_TIME_UNIT = TimeUnit.SECONDS
    }

    val anchor: BehaviorRelay<Pair<String?, BigDecimal>> =
        BehaviorRelay.createDefault(null to BigDecimal.ZERO)

    val currencies = MutableLiveData<List<Pair<String, BigDecimal>>>(listOf())

    init {
        anchor.switchMap { anchor ->
            Observable.interval(INITIAL_DELAY, INTERVAL_PERIOD, INTERVAL_TIME_UNIT)
                .flatMapSingle {
                    val list = currencies.value ?: listOf()
                    val request = FetchCurrenciesUseCase.Input(anchor, list)
                    fetchRatesUseCase.execute(request)
                }
                .map(FetchCurrenciesUseCase.Output::currencies)
                .doOnNext(currencies::postValue)
        }
            .retryWhen(fetchRatesRetryPolicy)
            .subscribeOn(Schedulers.io())
            .subscribeByViewModel()
    }
}
