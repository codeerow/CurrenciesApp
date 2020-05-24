package com.codeerow.pokenverter.data.repository

import com.codeerow.pokenverter.data.network.api.RatesAPI
import com.codeerow.pokenverter.domain.repository.RatesRepository
import io.reactivex.Single
import java.math.BigDecimal


class NetworkRatesRepository(private val api: RatesAPI) : RatesRepository {

    companion object {
        private const val ANCHOR_CURRENCY_RATE = "1"
    }

    override fun read(anchor: String?): Single<Map<String, BigDecimal>> =
        api.read(anchor).map {
            mutableMapOf<String, BigDecimal>().apply {
                put(it.baseCurrency, BigDecimal(ANCHOR_CURRENCY_RATE))
                putAll(it.rates)
            }
        }
}