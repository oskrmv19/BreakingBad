package com.oskr19.breakingbad.core.domain

import com.oskr19.breakingbad.core.ErrorMessages.MSG_CONNECTION_ERROR

sealed class Failure(msg: String?): Throwable(msg) {

    class ServerError(message: String? = null) : Failure(message)

    object NoConnection : Failure(MSG_CONNECTION_ERROR)

}