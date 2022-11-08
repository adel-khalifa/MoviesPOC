package com.adel.moviespoc.domain.repositories

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.CurrentPage
import com.adel.moviespoc.data.models.PaginatedResponse
import com.adel.moviespoc.data.source.interfaces.MoviesDetailsDataSource
import com.adel.moviespoc.data.source.interfaces.MoviesListDataSource
import com.adel.moviespoc.domain.entities.Movie
import com.adel.moviespoc.domain.entities.MovieDetails

class MoviesRepositoryImpl(
    private val listDataSource: MoviesListDataSource,
    val detailsDataSource: MoviesDetailsDataSource
) : MoviesRepository {
    override suspend fun getMoviesList(page: CurrentPage): Either<AppFailure, PaginatedResponse<Movie>> {
        return listDataSource.getMoviesList(page = page)
    }

    override suspend fun getMovieById(): Either<AppFailure, MovieDetails> {
        TODO("Not yet implemented")
    }
}