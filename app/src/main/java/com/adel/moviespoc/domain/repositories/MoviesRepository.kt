package com.adel.moviespoc.domain.repositories

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.CurrentPage
import com.adel.moviespoc.data.models.PaginatedResponse
import com.adel.moviespoc.domain.entities.Movie
import com.adel.moviespoc.domain.entities.MovieDetails

interface MoviesRepository {

    suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<Movie>>
    suspend fun getMovieById(): Either<AppFailure, MovieDetails>
}