package com.oskr19.breakingbad.core.presentation

import com.oskr19.breakingbad.core.domain.Failure

sealed class Event {

    object LOADING: Event()
    object FINISHED: Event()
    object DISCONNECTED: Event()

    class ERROR(cause: Failure): Event()

    fun isLoading() = this is LOADING

    fun isDisconnected() = this is DISCONNECTED

    fun isFinished() = this is FINISHED

    fun isError() = this is ERROR
}