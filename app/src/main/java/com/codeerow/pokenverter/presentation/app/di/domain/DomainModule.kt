package com.codeerow.pokenverter.presentation.app.di.domain

import com.codeerow.pokenverter.data.di.REAL
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.FetchCurrenciesUseCase
import org.koin.dsl.module


val domain = listOf(
    module {
        factory<UseCase<FetchCurrenciesUseCase.Input, FetchCurrenciesUseCase.Output>> {
            FetchCurrenciesUseCase(repository = get(REAL))
        }
    }
)
