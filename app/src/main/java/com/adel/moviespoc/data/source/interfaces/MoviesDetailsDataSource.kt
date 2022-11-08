package com.adel.moviespoc.data.source.interfaces

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.domain.entities.MovieDetails

interface MoviesDetailsDataSource {
    suspend fun getMovieDetails(): Either<AppFailure, MovieDetails>
}