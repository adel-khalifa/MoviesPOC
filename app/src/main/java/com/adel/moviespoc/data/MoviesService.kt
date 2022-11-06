package com.adel.moviespoc.data

import com.adel.moviespoc.data.models.MoviesResponse
import retrofit2.http.GET


interface MoviesService {

    @GET("discover/movie")
    suspend fun fetchMovies(): MoviesResponse

}
