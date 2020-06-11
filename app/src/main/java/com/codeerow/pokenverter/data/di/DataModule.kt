package com.codeerow.pokenverter.data.di

import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.data.di.network.networkModule
import com.codeerow.pokenverter.data.model.ApplicationException
import com.codeerow.pokenverter.data.network.connectivity.ConnectivityProvider
import com.codeerow.pokenverter.data.repository.NetworkRatesRepository
import com.codeerow.pokenverter.domain.repository.RatesRepository
import io.reactivex.Observable
import org.koin.dsl.module
import java.io.IOException


val data = networkModule + module {
    factory<RatesRepository> { NetworkRatesRepository(api = get()) }

    /** Retry policies */
    factory<(Throwable) -> Observable<*>> {
        val networkState: Observable<ConnectivityProvider.NetworkState> = get()
        return@factory { throwable ->
            if (throwable is IOException) networkState.filter { it is ConnectivityProvider.NetworkState.Connected }
            else Observable.error<ApplicationException>(
                ApplicationException(
                    R.string.application_error_default_message,
                    throwable
                )
            )
        }
    }
}
