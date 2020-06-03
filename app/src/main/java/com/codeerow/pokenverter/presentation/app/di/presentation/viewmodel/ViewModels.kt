package com.codeerow.pokenverter.presentation.app.di.presentation.viewmodel

import com.codeerow.pokenverter.data.network.connectivity.ConnectivityProvider
import com.codeerow.pokenverter.presentation.ui.screens.converter.ConverterViewModel
import io.reactivex.Observable
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.io.IOException


val viewModels = module {
    viewModel {
        val networkState: Observable<ConnectivityProvider.NetworkState> = get()
        val networkStateRetryPolicy: (Observable<Throwable>) -> Observable<*> = { error ->
            error.flatMap { throwable ->
                if (throwable is IOException) networkState.filter { it is ConnectivityProvider.NetworkState.Connected }
                else Observable.just(throwable)
            }
        }
        ConverterViewModel(
            fetchRatesUseCase = get(),
            fetchRatesRetryPolicy = networkStateRetryPolicy
        )
    }
}

