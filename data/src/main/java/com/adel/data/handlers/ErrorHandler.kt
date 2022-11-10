package com.adel.data.handlers

import arrow.core.Either
import arrow.core.left
import com.adel.data.R
import com.adel.data.models.AppFailure
import com.adel.data.models.ErrorMessage
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


suspend fun <T> safeCall(
    call: suspend () -> Either<AppFailure, T>
): Either<AppFailure, T> {
    return try {
        call()
    } catch (e: Throwable) {
        Timber.tag("Network-exception-logs").i(e.message.toString())
        e.toFailure().left()
    }
}

private fun Throwable.toFailure(): AppFailure {
    return when (this) {
        is HttpException -> {
            when (code()) {
                401 -> AppFailure.Unauthorized(ErrorMessage.LocalMessage(R.string.error_not_authorized))
                in 402 until 500 -> AppFailure.ServerError(ErrorMessage.ServerMessage(this.message()))
                in 500 until 600 -> AppFailure.ServerError(ErrorMessage.LocalMessage(R.string.error_server_busy))
                else -> AppFailure.General(ErrorMessage.LocalMessage(R.string.error_general))
            }
        }
        is IOException -> AppFailure.NetworkConnection(ErrorMessage.LocalMessage(R.string.error_network))
        else -> AppFailure.General(ErrorMessage.LocalMessage(R.string.error_general))
    }
}
