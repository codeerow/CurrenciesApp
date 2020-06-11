package com.codeerow.pokenverter.tests.viewmodel

import androidx.lifecycle.Observer
import com.codeerow.pokenverter.R
import com.codeerow.pokenverter.data.model.ApplicationException
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Input
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Output
import com.codeerow.pokenverter.presentation.di.domain.domain
import com.codeerow.pokenverter.presentation.di.presentation.presentation
import com.codeerow.pokenverter.presentation.ui.screens.converter.ConverterViewModel
import com.codeerow.pokenverter.tests.BaseTest
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.math.BigDecimal


class ConverterViewModelTest : BaseTest() {

    private val viewModel: ConverterViewModel by inject()


    @get:Rule
    val injectDependenciesRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(domain + presentation)
    }


    @Test
    fun `test currencies res property`() {
        val testObserver: Observer<List<Pair<String, BigDecimal>>> = mock()

        val input = Input(ConverterViewModel.DEFAULT_ANCHOR, listOf())
        val output = Output(listOf("1" to BigDecimal.ONE, "2" to BigDecimal.TEN))

        val expectedCurrencies = output.currencies

        declareMock<UseCase<Input, Observable<Output>>> {
            given(invoke(input)).willReturn(Observable.just(output))
        }

        viewModel.currencies.observeForever(testObserver)
        verify(testObserver, times(1)).onChanged(expectedCurrencies)
    }

    @Test
    fun `test error res property`() {
        val testObserver: Observer<Int> = mock()

        val input = Input(ConverterViewModel.DEFAULT_ANCHOR, listOf())
        val expectedErrorRes = R.string.application_error_default_message

        declareMock<UseCase<Input, Observable<Output>>> {
            given(invoke(input)).willReturn(Observable.fromCallable {
                throw ApplicationException(expectedErrorRes)
            })
        }

        viewModel.errorRes.observeForever(testObserver)
        verify(testObserver, times(1)).onChanged(expectedErrorRes)
    }
}