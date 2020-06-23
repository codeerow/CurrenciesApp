package com.codeerow.pokenverter.presentation.di.domain

import com.codeerow.pokenverter.domain.usecases.impl.FetchCurrencies
import org.koin.dsl.module


val useCasesModule = module {
    factory {
        FetchCurrencies(repository = get())
    }
}

val domain = useCasesModule
