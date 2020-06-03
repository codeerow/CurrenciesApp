package com.codeerow.pokenverter.data.repository

import com.codeerow.pokenverter.domain.repository.RatesRepository
import io.reactivex.Single
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random


class MockedRatesRepository : RatesRepository {

    companion object {
        private const val RATES_COUNT = 20
    }

    private val rates
        get() = List(RATES_COUNT) { Random.nextDouble(0.0, 10.0) }
            .map { BigDecimal.valueOf(it).setScale(3, RoundingMode.HALF_EVEN) }

    override fun read(anchor: String?): Single<Map<String, BigDecimal>> {
        return Single.fromCallable {
            rates.mapIndexed { index, randomRate ->
                val rate =
                    if (index.toString() == anchor) BigDecimal.ONE
                    else randomRate
                "$index" to rate
            }.toMap()
        }
    }
}