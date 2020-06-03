package com.codeerow.pokenverter.domain.usecases.impl

import com.codeerow.pokenverter.domain.repository.RatesRepository
import com.codeerow.pokenverter.domain.usecases.UseCase
import io.reactivex.Single
import java.math.BigDecimal


class FetchCurrenciesUseCase(private val repository: RatesRepository) :
    UseCase<FetchCurrenciesUseCase.Input, Single<FetchCurrenciesUseCase.Output>>() {

    data class Input(
        val anchor: Pair<String?, BigDecimal>,
        val currencies: List<Pair<String, BigDecimal>>
    )

    data class Output(
        val currencies: List<Pair<String, BigDecimal>>
    )


    override fun execute(input: Input): Single<Output> = with(input) {
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
                    Output(
                        newCurrencies
                    )
                }
            }
    }
}