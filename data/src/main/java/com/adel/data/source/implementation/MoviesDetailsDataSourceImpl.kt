package com.adel.data.source.implementation

import arrow.core.Either
import com.adel.data.dto.MovieDetailsDto
import com.adel.data.handlers.handleResponse
import com.adel.data.handlers.safeCall
import com.adel.data.services.MoviesService
import com.adel.data.source.interfaces.MoviesDetailsDataSource
import com.adel.models.AppFailure
import com.adel.models.values.MovieId

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