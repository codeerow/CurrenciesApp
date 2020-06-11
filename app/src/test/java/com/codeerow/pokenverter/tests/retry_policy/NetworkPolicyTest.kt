package com.codeerow.pokenverter.tests.retry_policy

import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.data.di.data
import com.codeerow.pokenverter.data.model.ApplicationException
import com.codeerow.pokenverter.data.network.connectivity.ConnectivityProvider
import com.codeerow.pokenverter.tests.BaseTest
import com.jakewharton.rxrelay2.PublishRelay
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.functions.Predicate
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.io.IOException


class NetworkPolicyTest : BaseTest() {

    private val networkPolicy by inject<(Throwable) -> Observable<*>>()
    private val connectivityRelay = PublishRelay.create<ConnectivityProvider.NetworkState>()

    @get:Rule
    val injectDependenciesRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(data + module {
            single<Observable<ConnectivityProvider.NetworkState>>(override = true) { connectivityRelay }
        })
    }


    @Test
    fun `test if policy work for expected IO exception`() {
        val observer = networkPolicy(IOException()).test()
            .assertNoErrors()
            .assertValueCount(0)

        val state = mock<ConnectivityProvider.NetworkState.Connected>()
        connectivityRelay.accept(state)

        observer.dispose()
    }

    @Test
    fun `test error resource if unexpected exception was thrown`() {
        val observer = networkPolicy(NullPointerException()).test()
            .assertFailure(Predicate {
                (it as? ApplicationException)?.messageRes == R.string.application_error_default_message
            })

        val state = mock<ConnectivityProvider.NetworkState.Connected>()
        connectivityRelay.accept(state)

        observer.dispose()
    }
}