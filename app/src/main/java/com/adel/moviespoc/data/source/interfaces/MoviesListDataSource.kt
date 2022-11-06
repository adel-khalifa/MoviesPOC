package com.adel.moviespoc.data.source.interfaces

import com.adel.moviespoc.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesListDataSource {
    fun getMoviesList(): Flow<List<Movie>>
}