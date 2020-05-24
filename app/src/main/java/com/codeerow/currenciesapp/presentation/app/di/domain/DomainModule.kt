package com.codeerow.currenciesapp.presentation.app.di.domain

import com.codeerow.currenciesapp.domain.usecases.ObserveRatesUseCase
import org.koin.dsl.module


val domain = listOf(
    module {
        factory { ObserveRatesUseCase(repository = get()) }
    }
)
