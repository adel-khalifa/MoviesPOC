package com.adel.moviespoc.data.repositories

import com.adel.moviespoc.data.source.interfaces.MoviesDetailsDataSource
import com.adel.moviespoc.data.source.interfaces.MoviesListDataSource
import com.adel.moviespoc.domain.entities.Movie
import com.adel.moviespoc.domain.entities.MovieDetails
import com.adel.moviespoc.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    val listDataSource: MoviesListDataSource,
    val detailsDataSource: MoviesDetailsDataSource
) : MoviesRepository {
    override fun getMoviesList(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }

    override fun getMovieById(): Flow<MovieDetails> {
        TODO("Not yet implemented")
    }
}