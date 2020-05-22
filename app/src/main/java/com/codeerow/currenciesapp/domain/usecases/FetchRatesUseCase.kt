package com.codeerow.currenciesapp.domain.usecases

import com.codeerow.currenciesapp.presentation.model.Currency
import io.reactivex.Single
import kotlin.random.Random


class FetchRatesUseCase {

    fun execute(request: Request): Single<List<Pair<Currency, Double>>> {
        return Single.just(
            listOf(
                Currency("1", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("2", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("3", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("4", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("5", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("6", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("7", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("8", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("9", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("10", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("11", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("12", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("13", "url", "title", "desc") to Random.nextDouble(0.0, 100.0),
                Currency("14", "url", "title", "desc") to Random.nextDouble(0.0, 100.0)
            )
        )
    }

    data class Request(private val anchorCurrency: String?, private val value: Double?)
}