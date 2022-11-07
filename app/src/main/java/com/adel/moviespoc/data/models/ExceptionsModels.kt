package com.adel.moviespoc.data.models


sealed class AppFailure {
    abstract val message: ErrorMessage?

    data class Unauthorized(override val message: ErrorMessage? = null) : AppFailure()
    data class ServerError(override val message: ErrorMessage? = null) : AppFailure()
    data class NetworkConnection(override val message: ErrorMessage? = null) : AppFailure()
    data class General(override val message: ErrorMessage? = null) : AppFailure()

}

sealed class ErrorMessage {
    data class ServerMessage(val message: String) : ErrorMessage()
    data class LocalMessage(val messageRes: Int) : ErrorMessage()
}