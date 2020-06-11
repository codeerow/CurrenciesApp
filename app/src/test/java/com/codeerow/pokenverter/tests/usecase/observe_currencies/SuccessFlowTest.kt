package com.codeerow.pokenverter.tests.usecase.observe_currencies

import com.codeerow.pokenverter.data.di.data
import com.codeerow.pokenverter.domain.repository.RatesRepository
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Input
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Output
import com.codeerow.pokenverter.presentation.di.domain.domain
import com.codeerow.pokenverter.tests.BaseTest
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.logger.Level
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import java.io.IOException
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


@RunWith(JUnit4::class)
class SuccessFlowTest : BaseTest() {

    private val observeCurrencies: UseCase<Input, Observable<Output>> by inject()
    private val error = IOException()


    @get:Rule
    val injectDependenciesRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(data + domain)

        /** declare always try to retry policy  */
        declareMock<(Throwable) -> Observable<*>> {
            given(invoke(error)).willReturn(Observable.just(Unit))
        }
    }


    @Test
    fun `test initial load`() {
        val anchor = null to BigDecimal.ONE
        val rates = mapOf<String, BigDecimal>(
            "1" to BigDecimal.ONE,
            "2" to BigDecimal.TEN
        )
        val expectedOutput = Output(rates.toList())

        declareMock<RatesRepository> {
            given(read(anchor.first)).willReturn(Single.just(rates))
        }

        observeCurrencies(Input(anchor, listOf()))
            .firstOrError()
            .test()
            .assertValue(expectedOutput)
            .dispose()
    }


    @Test
    fun `test load for specified anchor`() {
        val anchorKey = "2"
        val anchorValue = BigDecimal.TEN
        val rates = mapOf<String, BigDecimal>(
            "1" to BigDecimal.ONE,
            "2" to BigDecimal.TEN
        )
        val expectedOutput = Output(
            listOf(
                "2" to BigDecimal.TEN * anchorValue,
                "1" to BigDecimal.ONE * anchorValue
            )
        )

        declareMock<RatesRepository> {
            given(read(anchorKey)).willReturn(Single.just(rates))
        }

        observeCurrencies(Input(anchorKey to anchorValue, listOf()))
            .firstOrError()
            .test()
            .assertValue(expectedOutput)
            .dispose()
    }

    @Test
    fun `test time between loads`() {
        val testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }

        val anchor = null to BigDecimal.ONE

        declareMock<RatesRepository> {
            given(read(anchor.first)).willReturn(Single.just(mapOf()))
        }

        val observer = observeCurrencies(Input(anchor, listOf()))
            .test()

        testScheduler.advanceTimeBy(0, TimeUnit.SECONDS)
        observer.assertValueCount(1)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertValueCount(2)

        testScheduler.advanceTimeBy(100, TimeUnit.SECONDS)
        observer.assertValueCount(102)

        observer.dispose()
    }
}