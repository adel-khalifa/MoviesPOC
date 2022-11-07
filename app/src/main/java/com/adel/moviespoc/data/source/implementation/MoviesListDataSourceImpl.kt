package com.adel.moviespoc.data.source.implementation

import arrow.core.Either
import com.adel.moviespoc.data.mapper.mapResponseData
import com.adel.moviespoc.data.mapper.safeCall
import com.adel.moviespoc.data.models.AppFailure
import com.adel.moviespoc.data.models.Result
import com.adel.moviespoc.data.services.MoviesService
import com.adel.moviespoc.data.source.interfaces.MoviesListDataSource
import com.adel.moviespoc.domain.models.Movie

class MoviesListDataSourceImpl(private val moviesService: MoviesService) : MoviesListDataSource {
    override suspend fun getMoviesList(): Either<AppFailure, List<Movie>> {

        return safeCall {
            moviesService.fetchMoviesList().mapResponseData { response ->
                response.results?.mapNotNull { it?.toMovie() }
            }
        }
    }
}

fun Result.toMovie(): Movie? {
    return if (id == null || title == null) null
    else Movie(id, title)
}