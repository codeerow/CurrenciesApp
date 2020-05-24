package com.codeerow.pokenverter.data.di

import com.codeerow.pokenverter.data.di.network.networkModule
import com.codeerow.pokenverter.data.repository.NetworkRatesRepository
import com.codeerow.pokenverter.domain.repository.RatesRepository
import org.koin.dsl.module


val data = networkModule + module {
    factory<RatesRepository> { NetworkRatesRepository(api = get()) }
}

