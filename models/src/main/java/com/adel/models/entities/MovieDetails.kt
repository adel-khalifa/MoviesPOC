package com.adel.models.entities

@JvmInline
value class MovieGenres(val name: String)

data class ProductionCompanies(
    val logoPath: String,
    val name: String
)

data class MovieDetails(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String,
    val vote: Double,
    val votersCount: Int,
    val status: String,
    val genres: List<MovieGenres>,
    val productionCompanies: List<ProductionCompanies>,

    )
