package com.codeerow.pokenverter.presentation.app.di.domain

import com.codeerow.pokenverter.domain.usecases.ObserveRatesUseCase
import org.koin.dsl.module


val domain = listOf(
    module {
        factory { ObserveRatesUseCase(repository = get()) }
    }
)
