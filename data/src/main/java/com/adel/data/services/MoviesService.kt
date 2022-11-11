package com.adel.data.services

import com.adel.data.dto.MovieDetailsDto
import com.adel.data.dto.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MoviesService {

    @GET("discover/movie")
    suspend fun fetchMoviesList(@Query("page") page: Int? = null): Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun fetchMovieById(
        @Path(value = "movie_id") movieId: Int
    ): Response<MovieDetailsDto>

}
