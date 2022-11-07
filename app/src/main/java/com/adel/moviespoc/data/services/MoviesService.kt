package com.adel.moviespoc.data.services

import com.adel.moviespoc.data.models.MovieDetailsResponse
import com.adel.moviespoc.data.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface MoviesService {

    @GET("discover/movie")
    suspend fun fetchMoviesList(): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieById(
        @Path(value = "movie_id") movieId: Int
    ): Response<MovieDetailsResponse>

}
