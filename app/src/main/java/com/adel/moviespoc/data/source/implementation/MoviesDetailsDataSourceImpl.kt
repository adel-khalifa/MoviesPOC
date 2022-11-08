package com.adel.moviespoc.data.source.implementation

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.services.MoviesService
import com.adel.moviespoc.data.source.interfaces.MoviesDetailsDataSource
import com.adel.moviespoc.domain.entities.MovieDetails

class MoviesDetailsDataSourceImpl(val moviesService: MoviesService) : MoviesDetailsDataSource {
    override suspend fun getMovieDetails(): Either<AppFailure, MovieDetails> {
        TODO("Not yet implemented")
    }
}