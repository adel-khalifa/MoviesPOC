package com.adel.data.handlers

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.adel.data.R
import com.adel.data.dto.MovieDto
import com.adel.data.dto.MoviesResponse
import com.adel.data.dto.PaginatedResponse
import com.adel.models.AppFailure
import com.adel.models.ErrorMessage
import com.adel.models.values.CurrentPage
import com.adel.models.values.TotalPages
import retrofit2.Response


fun <T, R> Response<T>.handleResponse(extractResponseBody: (T) -> R): Either<AppFailure, R> {

    val responseBody = body()
    val code = code()
    val responseMessage = message()

    return if (isSuccessful && responseBody != null) extractResponseBody(responseBody).right()
    else handleErrorCode(code, responseMessage).left()

}

fun <T> Response<MoviesResponse>.handlePaginatedResponse(
    extractResponseBody: (MovieDto) -> T?
): Either<AppFailure, PaginatedResponse<T>> {

    val body = body()
    val code = code()
    val message = message()
    val data = body?.results
    return if (
        isSuccessful &&
        body != null &&
        body.page != null &&
        body.totalPages != null &&

        data != null
    ) {
        PaginatedResponse(
            page = CurrentPage(body.page),
            totalPages = TotalPages(body.totalPages),
            list = data.mapNotNull { element -> element?.let { extractResponseBody(it) } }
        ).right()
    } else handleErrorCode(code = code, message).left()
}


fun handleErrorCode(code: Int?, message: String?): AppFailure {
    return when (code) {
        401 -> AppFailure.Unauthorized(ErrorMessage.LocalMessage(R.string.error_not_authorized))
        404 -> AppFailure.NotFound(ErrorMessage.LocalMessage(R.string.url_not_found))
        403 -> AppFailure.AccessDenied(ErrorMessage.LocalMessage(R.string.access_denied))
        else -> AppFailure.ServerError(
            if (message != null) ErrorMessage.ServerMessage(message)
            else ErrorMessage.LocalMessage(R.string.error_general)
        )
    }
}
