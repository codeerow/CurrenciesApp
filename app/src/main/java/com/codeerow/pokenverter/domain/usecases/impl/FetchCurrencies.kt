package com.codeerow.pokenverter.domain.usecases.impl

import com.codeerow.pokenverter.domain.repository.RatesRepository
import com.codeerow.pokenverter.domain.usecases.UseCase
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.math.BigDecimal


class FetchCurrencies(
    private val repository: RatesRepository
) : UseCase<FetchCurrencies.Input, Observable<FetchCurrencies.Output>>() {

    data class Input(
        val anchor: Pair<String?, BigDecimal?>,
        val currencies: List<Pair<String, BigDecimal>>
    )

    sealed class Output {
        class Processing(val initial: Boolean) : Output()
        class Success(val currencies: List<Pair<String, BigDecimal>>) : Output()
        class Failure(val throwable: Throwable) : Output()
    }

    private val state: BehaviorSubject<Output> = BehaviorSubject.create()


    override fun invoke(input: Input): Observable<Output> {
        return readRates(input)
            .doOnSubscribe {
                val initial = input.currencies.isEmpty()
                state.onNext(Output.Processing(initial))
            }
            .doOnSuccess { state.onNext(Output.Success(it)) }
            .doOnError { state.onNext(Output.Failure(it)) }
            .flatMapObservable { state }
    }

    private fun readRates(input: Input): Single<List<Pair<String, BigDecimal>>> = with(input) {
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
                        val amount = rate * (input.anchor.second ?: BigDecimal.ONE)
                        it.first to amount
                    }
                    newCurrencies
                }
            }
    }
}