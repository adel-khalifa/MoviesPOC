package com.adel.models.entities

data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String,
    val vote: Double,
    val votersCount: Int
)
