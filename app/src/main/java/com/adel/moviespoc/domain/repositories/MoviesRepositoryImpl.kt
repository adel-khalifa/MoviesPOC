package com.adel.moviespoc.domain.repositories

import arrow.core.Either
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.source.interfaces.MoviesDetailsDataSource
import com.adel.moviespoc.data.source.interfaces.MoviesListDataSource
import com.adel.moviespoc.domain.models.Movie
import com.adel.moviespoc.domain.models.MovieDetails

class MoviesRepositoryImpl(
    private val listDataSource: MoviesListDataSource,
    val detailsDataSource: MoviesDetailsDataSource
) : MoviesRepository {
    override suspend fun getMoviesList(): Either<AppFailure, List<Movie>> {
        return listDataSource.getMoviesList()
    }

    override suspend fun getMovieById(): Either<AppFailure, MovieDetails> {
        TODO("Not yet implemented")
    }
}