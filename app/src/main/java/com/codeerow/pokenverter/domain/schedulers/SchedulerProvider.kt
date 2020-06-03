package com.codeerow.pokenverter.domain.schedulers

import io.reactivex.Scheduler


interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}