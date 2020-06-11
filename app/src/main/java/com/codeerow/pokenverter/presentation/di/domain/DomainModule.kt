package com.codeerow.pokenverter.presentation.di.domain

import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Input
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Output
import io.reactivex.Observable
import org.koin.dsl.module


val useCasesModule = module {
    factory<UseCase<Input, Observable<Output>>> {
        ObserveCurrencies(
            repository = get(),
            retryPolicy = get()
        )
    }
}

val domain = useCasesModule
