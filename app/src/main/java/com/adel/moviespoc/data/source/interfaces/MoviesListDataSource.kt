package com.adel.moviespoc.data.source.interfaces

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.CurrentPage
import com.adel.moviespoc.data.models.PaginatedResponse
import com.adel.moviespoc.domain.entities.Movie

interface MoviesListDataSource {
    suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<Movie>>
}