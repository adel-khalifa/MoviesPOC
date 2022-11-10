package com.adel.bussiness.repositories

import arrow.core.Either
import com.adel.data.models.*
import com.adel.data.source.interfaces.MoviesDetailsDataSource
import com.adel.data.source.interfaces.MoviesListDataSource


class MoviesRepositoryImpl(
    private val listDataSource: MoviesListDataSource,
    private val detailsDataSource: MoviesDetailsDataSource
) : MoviesRepository {
    override suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<MovieDto>> {
        return listDataSource.getMoviesList(page = page)
    }

    override suspend fun getMovieById(movieId: MovieId): Either<AppFailure, MovieDetailsDto> {
        return detailsDataSource.getMovieDetails(movieId = movieId)
    }
}