package com.codeerow.pokenverter.tests.usecase.observe_currencies

import com.codeerow.pokenverter.tests.BaseTest


class FailureFlowTest : BaseTest() {

    // TODO: Adapt
//    private val observeCurrencies: UseCase<Input, Observable<Output>> by inject()
//
//
//    @get:Rule
//    val injectDependenciesRule = KoinTestRule.create {
//        printLogger(Level.DEBUG)
//        modules(data + domain)
//    }
//
//
//    @Test
//    fun `test retry policy success`() {
//        val anchor = null to null
//        val rates = mapOf<String, BigDecimal>(
//            "1" to BigDecimal.ONE,
//            "2" to BigDecimal.TEN
//        )
//        val error = IOException()
//
//        declareMock<RatesRepository> {
//            var throwException = true
//            given(read(anchor.first)).willReturn(
//                Single.fromCallable {
//                    if (!throwException) rates
//                    else {
//                        throwException = false
//                        throw error
//                    }
//                }
//            )
//        }
//
//        declareMock<(Throwable) -> Observable<*>> {
//            given(invoke(error)).willReturn(Observable.just(Unit))
//        }
//
//        observeCurrencies(Input(anchor, listOf()))
//            .firstOrError()
//            .test()
//            .assertNoErrors()
//            .assertValue(Output(rates.toList()))
//            .dispose()
//    }
//
//    @Test
//    fun `test retry policy failure`() {
//        val anchor = "1" to BigDecimal.TEN
//        val error = IOException()
//
//        declareMock<RatesRepository> {
//            given(read(anchor.first)).willReturn(Single.error(error))
//        }
//        declareMock<(Throwable) -> Observable<*>> {
//            given(invoke(error)).willReturn(Observable.error<Throwable>(error))
//        }
//
//        observeCurrencies(Input(anchor, listOf()))
//            .test()
//            .assertFailure(error::class.java)
//            .dispose()
//    }
}