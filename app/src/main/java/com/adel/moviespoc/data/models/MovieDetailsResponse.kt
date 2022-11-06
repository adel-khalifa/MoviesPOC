package com.adel.moviespoc.data.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    val adult: Boolean? = null,
    @Json(name = "backdrop_path")
    val backdropPath: String? = null,
    @Json(name = "belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Int? = null,
    val genres: List<Genre?>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    @Json(name = "imdb_id")
    val imdbId: String? = null,
    @Json(name = "original_language")
    val originalLanguage: String? = null,
    @Json(name = "original_title")
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    @Json(name = "poster_path")
    val posterPath: String? = null,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompany?>? = null,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry?>? = null,
    @Json(name = "release_date")
    val releaseDate: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    @Json(name = "vote_average")
    val voteAverage: Double? = null,
    @Json(name = "vote_count")
    val voteCount: Int? = null
) {
    @JsonClass(generateAdapter = true)
    data class BelongsToCollection(
        @Json(name = "backdrop_path")
        val backdropPath: String? = null,
        val id: Int? = null,
        val name: String? = null,
        @Json(name = "poster_path")
        val posterPath: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class Genre(
        val id: Int? = null,
        val name: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCompany(
        val id: Int? = null,
        @Json(name = "logo_path")
        val logoPath: Any? = null,
        val name: String? = null,
        @Json(name = "origin_country")
        val originCountry: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCountry(
        @Json(name = "iso_3166_1")
        val iso31661: String? = null,
        val name: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class SpokenLanguage(
        @Json(name = "english_name")
        val englishName: String? = null,
        @Json(name = "iso_639_1")
        val iso6391: String? = null,
        val name: String? = null
    )
}