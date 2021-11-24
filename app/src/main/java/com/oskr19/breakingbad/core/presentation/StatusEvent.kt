package com.oskr19.breakingbad.core.presentation

interface StatusEvent {
    fun onLoading()
    fun onFinished()
    fun onError(event: Event?)
    fun onDisconnected()
}
