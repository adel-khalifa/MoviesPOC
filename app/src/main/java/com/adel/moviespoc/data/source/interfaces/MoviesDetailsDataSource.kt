package com.adel.moviespoc.data.source.interfaces

import com.adel.moviespoc.domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesDetailsDataSource {
    fun getMovieDetails(): Flow<MovieDetails>
}