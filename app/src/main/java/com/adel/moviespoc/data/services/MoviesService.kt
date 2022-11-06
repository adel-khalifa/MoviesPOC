package com.adel.moviespoc.data.services

import com.adel.moviespoc.data.models.MovieDetailsResponse
import com.adel.moviespoc.data.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface MoviesService {

    @GET("discover/movie")
    suspend fun fetchMovies(): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun fetchMovieById(
        @Path(value = "movie_id", encoded = true) movieId: Int
    ): MovieDetailsResponse

}
