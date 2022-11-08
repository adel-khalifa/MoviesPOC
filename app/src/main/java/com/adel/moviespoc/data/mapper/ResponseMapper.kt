package com.adel.moviespoc.data.mapper

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.adel.moviespoc.R
import com.adel.moviespoc.data.models.*
import retrofit2.HttpException
import retrofit2.Response
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

fun <T, R> Response<T>.mapResponseData(transform: (T) -> R?): Either<AppFailure, R> {

    val responseBody = body()
    val responseMessage = message()

    if (isSuccessful && responseBody != null) return transform(responseBody)?.right()
        ?: return AppFailure.ServerError().left()

    return handleErrorCode(responseMessage).left()

}

fun <T> Response<MoviesResponse>.mapPaginatedMovies(
    transform: (RemoteMovie) -> T?
): Either<AppFailure, PaginatedResponse<T>> {

    val body = body()
    val message = message()
    val data = body?.results
    return if (
        isSuccessful && body != null && body.page != null && body.totalPages != null && data != null) Either.Right(
        PaginatedResponse(
            page = CurrentPage(body.page),
            totalPages = TotalPages(body.totalPages),
            list = data.mapNotNull { element -> element?.let { transform(it) } }
        )
    ) else handleErrorCode(message).left()
}


fun handleErrorCode(message: String?) = AppFailure.ServerError(
    if (message != null) ErrorMessage.ServerMessage(message)
    else ErrorMessage.LocalMessage(R.string.error_general)
)


private fun Throwable.toFailure(): AppFailure {
    return when (this) {
        is HttpException -> {
            when (code()) {
                401 -> AppFailure.Unauthorized(ErrorMessage.LocalMessage(R.string.error_not_authorized))
                in 500 until 600 -> AppFailure.ServerError(ErrorMessage.LocalMessage(R.string.error_server_busy))
                else -> AppFailure.General(ErrorMessage.LocalMessage(R.string.error_general))
            }
        }
        is IOException -> AppFailure.NetworkConnection(ErrorMessage.LocalMessage(R.string.error_network))
        else -> AppFailure.General(ErrorMessage.LocalMessage(R.string.error_general))
    }
}
