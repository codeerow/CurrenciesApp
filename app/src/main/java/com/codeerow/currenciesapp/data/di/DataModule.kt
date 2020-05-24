package com.codeerow.currenciesapp.data.di

import com.codeerow.currenciesapp.data.di.network.networkModule
import com.codeerow.currenciesapp.data.repository.NetworkRatesRepository
import com.codeerow.currenciesapp.domain.repository.RatesRepository
import org.koin.dsl.module


val data = networkModule + module {
    factory<RatesRepository> { NetworkRatesRepository(api = get()) }
}

