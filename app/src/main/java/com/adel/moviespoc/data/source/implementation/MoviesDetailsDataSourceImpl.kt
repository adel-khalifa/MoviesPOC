package com.adel.moviespoc.data.source.implementation

import arrow.core.Either
import com.adel.moviespoc.data.handlers.handleResponse
import com.adel.moviespoc.data.handlers.safeCall
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.MovieDetailsDto
import com.adel.moviespoc.data.models.MovieId
import com.adel.moviespoc.data.services.MoviesService
import com.adel.moviespoc.data.source.interfaces.MoviesDetailsDataSource

class MoviesDetailsDataSourceImpl(private val moviesService: MoviesService) :
    MoviesDetailsDataSource {
    override suspend fun getMovieDetails(movieId: MovieId): Either<AppFailure, MovieDetailsDto> {
        return safeCall {
            moviesService.fetchMovieById(movieId.value).handleResponse { movieDetailsDto ->
                movieDetailsDto
            }
        }
    }
}