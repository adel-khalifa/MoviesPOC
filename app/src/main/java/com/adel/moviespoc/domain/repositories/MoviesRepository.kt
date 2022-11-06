package com.adel.moviespoc.domain.repositories

import com.adel.moviespoc.domain.entities.Movie
import com.adel.moviespoc.domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMoviesList(): Flow<List<Movie>>
    fun getMovieById(): Flow<MovieDetails>
}