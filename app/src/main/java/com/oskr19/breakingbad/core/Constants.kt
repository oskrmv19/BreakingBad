package com.oskr19.breakingbad.core

object Constants {
    const val DATABASE_NAME = "local_brakingbad.database"
    const val FETCH_LIMIT = 10
    const val EMTPY = ""
}

object ErrorMessages {
    const val MSG_NOT_FOUND = "Service/Resource not found!"
    const val MSG_GENERIC_ERROR = "Something went wrong. Please try again later."
    const val MSG_CONNECTION_ERROR = "Connection error!"
}

object ErrorCodes {
    const val NOT_FOUND = 404
    const val INTERNAL_SERVER_ERROR = 500
}

object EndPoints {
    const val CHARACTERS = "characters"
}