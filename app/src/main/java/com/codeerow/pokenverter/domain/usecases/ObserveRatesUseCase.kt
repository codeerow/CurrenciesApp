package com.codeerow.pokenverter.domain.usecases

import com.codeerow.pokenverter.domain.repository.RatesRepository
import io.reactivex.Observable
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


class ObserveRatesUseCase(private val repository: RatesRepository) {

    companion object {
        private const val INITIAL_DELAY = 0L
        private const val INTERVAL_PERIOD = 1L
        private val INTERVAL_TIME_UNIT = TimeUnit.SECONDS
    }

    fun execute(request: Request): Observable<Map<String, BigDecimal>> = with(request) {
        return Observable.interval(INITIAL_DELAY, INTERVAL_PERIOD, INTERVAL_TIME_UNIT)
            .flatMapSingle { repository.read(anchor) }
    }

    data class Request(val anchor: String?)
}