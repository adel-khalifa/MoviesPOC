package com.adel.domain.repositories

import arrow.core.Either
import com.adel.data.dto.MovieDetailsDto
import com.adel.data.dto.MovieDto
import com.adel.data.dto.PaginatedResponse
import com.adel.models.AppFailure
import com.adel.models.values.CurrentPage
import com.adel.models.values.MovieId

interface MoviesRepository {
    suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>>
    suspend fun getMovieById(movieId: MovieId): Either<AppFailure, MovieDetailsDto>
}