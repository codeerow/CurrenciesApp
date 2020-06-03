package com.codeerow.pokenverter.domain.usecases.impl

import com.codeerow.pokenverter.domain.repository.RatesRepository
import com.codeerow.pokenverter.domain.schedulers.SchedulerProvider
import com.codeerow.pokenverter.domain.usecases.UseCase
import io.reactivex.Observable
import io.reactivex.Single
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class ObserveCurrencies(
    private val repository: RatesRepository,
    private val retryPolicy: (Observable<Throwable>) -> Observable<*>,
    private val schedulers: SchedulerProvider
) : UseCase<ObserveCurrencies.Input, Observable<ObserveCurrencies.Output>>() {

    companion object {
        private const val INITIAL_DELAY = 0L
        private const val INTERVAL_PERIOD = 1L
        private val TIME_UNIT = TimeUnit.SECONDS
    }

    data class Input(
        val anchor: Pair<String?, BigDecimal>,
        val currencies: List<Pair<String, BigDecimal>>
    )

    data class Output(val currencies: List<Pair<String, BigDecimal>>)

    override fun invoke(input: Input): Observable<Output> {
        return Observable.interval(
            INITIAL_DELAY,
            INTERVAL_PERIOD,
            TIME_UNIT,
            schedulers.computation()
        ).flatMapSingle { readRates(input) }.retryWhen(retryPolicy)
    }

    private fun readRates(input: Input): Single<Output> = with(input) {
        return repository.read(anchor.first)
            .map { rates ->
                val currencies = (if (input.currencies.isEmpty()) rates.toList()
                else input.currencies).toMutableList()

                with(currencies) {
                    find { it.first == input.anchor.first }?.let { anchor ->
                        remove(anchor)
                        add(0, anchor)
                    }
                    val newCurrencies = map {
                        val rate = rates.getOrDefault(it.first, BigDecimal.ZERO)
                        val amount = rate * input.anchor.second
                        it.first to amount
                    }
                    Output(newCurrencies)
                }
            }
    }
}