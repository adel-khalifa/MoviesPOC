package com.adel.moviespoc.data.source.implementation

import com.adel.moviespoc.data.services.MoviesService
import com.adel.moviespoc.data.source.interfaces.MoviesListDataSource
import com.adel.moviespoc.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class MoviesListDataSourceImpl(val moviesService: MoviesService) : MoviesListDataSource {
    override fun getMoviesList(): Flow<List<Movie>> {
        TODO("Not yet implemented")
    }
}