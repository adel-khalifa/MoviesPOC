package com.adel.data.source.interfaces

import arrow.core.Either
import com.adel.data.models.MovieDetailsDto
import com.adel.models.AppFailure
import com.adel.models.values.MovieId

interface MoviesDetailsDataSource {
    suspend fun getMovieDetails(movieId: MovieId): Either<AppFailure, MovieDetailsDto>
}