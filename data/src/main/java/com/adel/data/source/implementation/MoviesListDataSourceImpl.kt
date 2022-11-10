package com.adel.data.source.implementation

import arrow.core.Either
import com.adel.data.handlers.handlePaginatedResponse
import com.adel.data.handlers.safeCall
import com.adel.data.models.MovieDto
import com.adel.data.models.PaginatedResponse
import com.adel.data.services.MoviesService
import com.adel.data.source.interfaces.MoviesListDataSource
import com.adel.models.AppFailure
import com.adel.models.values.CurrentPage

class MoviesListDataSourceImpl(private val moviesService: MoviesService) : MoviesListDataSource {
    override suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>> {

        return safeCall {
            moviesService.fetchMoviesList(page.value).handlePaginatedResponse(
                extractResponseBody = { movieDto -> movieDto }
            )
        }
    }
}
