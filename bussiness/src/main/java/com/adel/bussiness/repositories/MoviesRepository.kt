package com.adel.bussiness.repositories

import arrow.core.Either
import com.adel.data.models.*

interface MoviesRepository {
    suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>>
    suspend fun getMovieById(movieId: MovieId): Either<AppFailure, MovieDetailsDto>
}