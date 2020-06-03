package com.codeerow.pokenverter.domain.usecases


abstract class UseCase<I, O> {
    abstract fun execute(input: I): O
}