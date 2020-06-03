package com.codeerow.pokenverter

import com.codeerow.pokenverter.data.schedulers.TrampolineSchedulerProvider
import com.codeerow.pokenverter.domain.repository.RatesRepository
import com.codeerow.pokenverter.domain.usecases.UseCase
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Input
import com.codeerow.pokenverter.domain.usecases.impl.ObserveCurrencies.Output
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.math.BigDecimal


@RunWith(JUnit4::class)
class ObserveCurrenciesTest {

    @Mock
    lateinit var repository: RatesRepository

    private val mockRetryPolicy: (Observable<Throwable>) -> Observable<*> = { error ->
        error.flatMap { throwable -> Observable.just(throwable) }
    }

    private lateinit var observeCurrencies: UseCase<Input, Observable<Output>>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val schedulers = TrampolineSchedulerProvider()
        observeCurrencies = ObserveCurrencies(repository, mockRetryPolicy, schedulers)
    }


    @Test
    fun testNullAnchor() {
        val anchor = null to BigDecimal.ONE
        val rates = mapOf<String, BigDecimal>(
            "1" to BigDecimal.ONE,
            "2" to BigDecimal.TEN
        )
        val currencies = listOf(
            "1" to BigDecimal.ONE,
            "2" to BigDecimal.TEN
        )
        val expectedOutput = Output(rates.toList())

        `when`(repository.read(anchor.first)).thenReturn(Single.just(rates))

        val observer = observeCurrencies(Input(anchor, currencies))
            .firstOrError()
            .test()

        observer.assertValue(expectedOutput)
        observer.dispose()
    }

    @Test
    fun testNotNullAnchor() {
        val anchor = "2" to BigDecimal.TEN
        val rates = mapOf<String, BigDecimal>(
            "1" to BigDecimal.ONE,
            "2" to BigDecimal.TEN
        )
        val currencies = listOf(
            "1" to BigDecimal.ONE,
            "2" to BigDecimal.TEN
        )
        val expectedOutput = Output(
            listOf(
                "2" to BigDecimal.valueOf(100, 0),
                "1" to BigDecimal.TEN
            )
        )

        `when`(repository.read(anchor.first)).thenReturn(Single.just(rates))

        val observer = observeCurrencies(Input(anchor, currencies))
            .firstOrError()
            .test()

        observer.assertValue(expectedOutput)
        observer.dispose()
    }
}