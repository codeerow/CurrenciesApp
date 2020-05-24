package com.codeerow.currenciesapp.domain.repository

import io.reactivex.Single
import java.math.BigDecimal


interface RatesRepository {
    fun read(anchor: String?): Single<Map<String, BigDecimal>>
}