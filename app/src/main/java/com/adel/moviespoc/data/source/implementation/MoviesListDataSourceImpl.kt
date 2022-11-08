package com.adel.moviespoc.data.source.implementation

import arrow.core.Either
import com.adel.moviespoc.data.mapper.mapPaginatedMovies
import com.adel.moviespoc.data.mapper.safeCall
import com.adel.moviespoc.data.mapper.toMovie
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.CurrentPage
import com.adel.moviespoc.data.models.PaginatedResponse
import com.adel.moviespoc.data.services.MoviesService
import com.adel.moviespoc.data.source.interfaces.MoviesListDataSource
import com.adel.moviespoc.domain.entities.Movie

class MoviesListDataSourceImpl(private val moviesService: MoviesService) : MoviesListDataSource {
    override suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<Movie>> {

        return safeCall {
            moviesService.fetchMoviesList(page = page.value)
                .mapPaginatedMovies { it.toMovie() }
        }
    }
}
