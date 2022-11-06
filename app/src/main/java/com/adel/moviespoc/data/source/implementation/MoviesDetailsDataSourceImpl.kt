package com.adel.moviespoc.data.source.implementation

import com.adel.moviespoc.data.services.MoviesService
import com.adel.moviespoc.data.source.interfaces.MoviesDetailsDataSource
import com.adel.moviespoc.domain.entities.MovieDetails
import kotlinx.coroutines.flow.Flow

class MoviesDetailsDataSourceImpl(val moviesService: MoviesService) : MoviesDetailsDataSource {
    override fun getMovieDetails(): Flow<MovieDetails> {
        TODO("Not yet implemented")
    }
}