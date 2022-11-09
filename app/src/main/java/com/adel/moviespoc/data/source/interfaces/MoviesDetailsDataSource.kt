package com.adel.moviespoc.data.source.interfaces

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.MovieDetailsDto
import com.adel.moviespoc.data.models.MovieId

interface MoviesDetailsDataSource {
    suspend fun getMovieDetails(movieId: MovieId): Either<AppFailure, MovieDetailsDto>
}