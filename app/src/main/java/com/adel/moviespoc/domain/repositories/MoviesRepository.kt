package com.adel.moviespoc.domain.repositories

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.domain.models.Movie
import com.adel.moviespoc.domain.models.MovieDetails

interface MoviesRepository {

    suspend fun getMoviesList(): Either<AppFailure, List<Movie>>
    suspend fun getMovieById(): Either<AppFailure, MovieDetails>
}