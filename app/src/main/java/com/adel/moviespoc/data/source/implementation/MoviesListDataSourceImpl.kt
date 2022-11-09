package com.adel.moviespoc.data.source.implementation

import arrow.core.Either
import com.adel.moviespoc.data.handlers.handlePaginatedResponse
import com.adel.moviespoc.data.handlers.safeCall
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.CurrentPage
import com.adel.moviespoc.data.models.MovieDto
import com.adel.moviespoc.data.models.PaginatedResponse
import com.adel.moviespoc.data.services.MoviesService
import com.adel.moviespoc.data.source.interfaces.MoviesListDataSource

class MoviesListDataSourceImpl(private val moviesService: MoviesService) : MoviesListDataSource {
    override suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>> {

        return safeCall {
            moviesService.fetchMoviesList(page.value).handlePaginatedResponse(
                extractResponseBody = { movieDto -> movieDto }
            )
        }
    }
}
