package com.adel.moviespoc.domain.repositories

import arrow.core.Either
import com.adel.moviespoc.data.models.*

interface MoviesRepository {
    suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>>
    suspend fun getMovieById(movieId: MovieId): Either<AppFailure, MovieDetailsDto>
}