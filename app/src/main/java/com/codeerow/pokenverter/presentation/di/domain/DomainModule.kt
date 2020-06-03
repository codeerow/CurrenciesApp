package com.codeerow.pokenverter.presentation.di.domain

import com.codeerow.pokenverter.data.di.REAL
import com.codeerow.pokenverter.data.network.connectivity.ConnectivityProvider
import com.codeerow.pokenverter.data.schedulers.AndroidSchedulerProvider
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies
import io.reactivex.Observable
import org.koin.dsl.module
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Input
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Output
import java.io.IOException


val domain = listOf(
    module {
        factory<UseCase<Input, Observable<Output>>> {

            val networkState: Observable<ConnectivityProvider.NetworkState> = get()
            val networkStateRetryPolicy: (Observable<Throwable>) -> Observable<*> = { error ->
                error.flatMap { throwable ->
                    if (throwable is IOException) networkState.filter { it is ConnectivityProvider.NetworkState.Connected }
                    else Observable.just(throwable)
                }
            }
            val schedulers = AndroidSchedulerProvider()
            ObserveCurrencies(
                repository = get(REAL),
                retryPolicy = networkStateRetryPolicy,
                schedulers = schedulers
            )
        }
    }
)
