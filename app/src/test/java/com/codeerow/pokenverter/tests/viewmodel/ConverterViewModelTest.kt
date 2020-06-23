package com.codeerow.pokenverter.tests.viewmodel

import com.codeerow.pokenverter.presentation.di.domain.domain
import com.codeerow.pokenverter.presentation.di.presentation.presentation
import com.codeerow.pokenverter.presentation.ui.screens.converter.ConverterViewModel
import com.codeerow.pokenverter.tests.BaseTest
import org.junit.Rule
import org.koin.core.logger.Level
import org.koin.test.KoinTestRule
import org.koin.test.inject


class ConverterViewModelTest : BaseTest() {

    private val viewModel: ConverterViewModel by inject()


    @get:Rule
    val injectDependenciesRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(domain + presentation)
    }

        // TODO: Adapt
//    @Test
//    fun `test currencies res property`() {
//        val testObserver: Observer<List<Pair<String, BigDecimal>>> = mock()
//
//        val input = Input(ConverterViewModel.DEFAULT_ANCHOR, listOf())
//        val output = Output(listOf("1" to BigDecimal.ONE, "2" to BigDecimal.TEN))
//
//        val expectedCurrencies = output.currencies
//
//        declareMock<UseCase<Input, Observable<Output>>> {
//            given(invoke(input)).willReturn(Observable.just(output))
//        }
//
//        viewModel.currencies.observeForever(testObserver)
//        verify(testObserver, times(1)).onChanged(expectedCurrencies)
//    }

    // TODO: Adapt
//    @Test
//    fun `test error res property`() {
//        val testObserver: Observer<Int> = mock()
//
//        val input = Input(ConverterViewModel.DEFAULT_ANCHOR, listOf())
//        val expectedErrorRes = R.string.application_error_default_message
//
//        declareMock<UseCase<Input, Observable<Output>>> {
//            given(invoke(input)).willReturn(Observable.fromCallable {
//                throw ApplicationException(expectedErrorRes)
//            })
//        }
//
//        viewModel.errorRes.observeForever(testObserver)
//        verify(testObserver, times(1)).onChanged(expectedErrorRes)
//    }
}