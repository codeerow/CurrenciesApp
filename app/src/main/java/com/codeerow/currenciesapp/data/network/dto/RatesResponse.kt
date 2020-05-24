package com.codeerow.currenciesapp.data.network.dto

import java.math.BigDecimal


data class RatesResponse(
    val baseCurrency: String,
    val rates: Map<String, BigDecimal>
)