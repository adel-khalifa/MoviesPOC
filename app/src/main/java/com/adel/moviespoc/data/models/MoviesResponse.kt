package com.adel.moviespoc.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoviesResponse(
    val page: Int? = null,
    val results: List<Result?>? = null,
    @Json(name = "total_pages")
    val totalPages: Int? = null,
    @Json(name = "total_results")
    val totalResults: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class Result(
        val adult: Boolean? = null,
        @Json(name = "backdrop_path")
        val backdropPath: String? = null,
        @Json(name = "genre_ids")
        val genreIds: List<Int?>? = null,
        val id: Int? = null,
        @Json(name = "original_language")
        val originalLanguage: String? = null,
        @Json(name = "original_title")
        val originalTitle: String? = null,
        val overview: String? = null,
        val popularity: Double? = null,
        @Json(name = "poster_path")
        val posterPath: String? = null,
        @Json(name = "release_date")
        val releaseDate: String? = null,
        val title: String? = null,
        val video: Boolean? = null,
        @Json(name = "vote_average")
        val voteAverage: Double? = null,
        @Json(name = "vote_count")
        val voteCount: Int? = null
    )
}