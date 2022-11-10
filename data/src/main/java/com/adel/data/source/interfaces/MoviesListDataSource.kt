package com.adel.data.source.interfaces

import arrow.core.Either
import com.adel.data.models.MovieDto
import com.adel.data.models.PaginatedResponse
import com.adel.models.AppFailure
import com.adel.models.values.CurrentPage

interface MoviesListDataSource {
    suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>>
}