package com.oskr19.breakingbad.core.domain

abstract class BaseUseCase<out T, in P> where T : Any {

    abstract suspend fun run(params: P): T

    protected suspend operator fun invoke(
        params: P,
        onResult: (T) -> Unit = {}
    ) {
        onResult(run(params))
    }
}