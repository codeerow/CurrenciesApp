package com.codeerow.pokenverter.domain.usecases

import io.reactivex.Single


abstract class UseCase<I, O> {
    abstract fun execute(input: I): Single<O>
}