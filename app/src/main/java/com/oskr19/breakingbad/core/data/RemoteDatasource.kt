package com.oskr19.breakingbad.core.data

import com.oskr19.breakingbad.core.ErrorCodes
import com.oskr19.breakingbad.core.ErrorMessages.MSG_GENERIC_ERROR
import com.oskr19.breakingbad.core.ErrorMessages.MSG_NOT_FOUND
import com.oskr19.breakingbad.core.domain.Failure
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

open class RemoteDatasource {
    private val _tag = "Remote Datasource Error"

    protected suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Flow<T> {
        return flow {
            try {
                val response = call()
                if (response.isSuccessful) {
                    response.body()?.let { emit(it) } ?: throw validateByCode(response.code())
                } else {
                    throw HttpException(response)
                }
            } catch (e: Exception) {
                throw resolveFailure(e)
            }
        }
    }

    private fun resolveFailure(e: Exception): Failure {
        Timber.e(e, _tag)
        return when (e) {
            is SocketTimeoutException -> {
                Failure.ServerError(MSG_GENERIC_ERROR)
            }
            is ConnectException -> {
                Failure.NoConnection
            }
            is UnknownHostException -> {
                Failure.NoConnection
            }
            else -> {
                validateByCode(e)
            }
        }
    }

    private fun validateByCode(e: Exception): Failure {
        return if (e is HttpException) {
            validateByCode(e.code())
        } else {
            Failure.ServerError(e.message)
        }
    }

    private fun validateByCode(code: Int): Failure {
        return when (code) {
            ErrorCodes.INTERNAL_SERVER_ERROR -> {
                Failure.ServerError(MSG_GENERIC_ERROR)
            }
            ErrorCodes.NOT_FOUND -> {
                Failure.ServerError(MSG_NOT_FOUND)
            }
            else -> {
                Failure.ServerError(MSG_GENERIC_ERROR)
            }
        }
    }
}