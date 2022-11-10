package com.adel.data.source.interfaces

import arrow.core.Either
import com.adel.data.models.AppFailure
import com.adel.data.models.CurrentPage
import com.adel.data.models.MovieDto
import com.adel.data.models.PaginatedResponse

interface MoviesListDataSource {
    suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>>
}