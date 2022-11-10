package com.adel.domain.repositories

import arrow.core.Either
import com.adel.data.models.MovieDetailsDto
import com.adel.data.models.MovieDto
import com.adel.data.models.PaginatedResponse
import com.adel.data.source.interfaces.MoviesDetailsDataSource
import com.adel.data.source.interfaces.MoviesListDataSource
import com.adel.models.AppFailure
import com.adel.models.values.CurrentPage
import com.adel.models.values.MovieId


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