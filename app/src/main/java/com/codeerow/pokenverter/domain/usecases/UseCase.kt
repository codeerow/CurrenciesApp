package com.codeerow.pokenverter.domain.usecases


abstract class UseCase<I, O> {
    abstract operator fun invoke(input: I): O
}