package com.adel.domain.mapper

import com.adel.data.models.MovieDetailsDto
import com.adel.data.models.MovieDto
import com.adel.models.Movie
import com.adel.models.MovieDetails
import com.adel.models.MovieGenres
import com.adel.models.ProductionCompanies

private const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/original/"

fun MovieDto.toMovie(): Movie? {
    return if (
        title == null ||
        overview == null ||
        voteAverage == null ||
        posterPath == null ||
        voteCount == null ||
        id == null
    ) null
    else Movie(
        id = id!!,
        title = title!!,
        posterUrl = "$IMAGES_BASE_URL$posterPath",
        overview = overview!!,
        vote = voteAverage!!,
        votersCount = voteCount!!
    )

}


fun MovieDetailsDto.toMovieDetails(): MovieDetails? {
    return if (
        id == null ||
        title == null ||
        overview == null ||
        voteAverage == null ||
        posterPath == null ||
        voteCount == null ||
        status == null ||
        genres == null ||
        productionCompanies == null
    ) null
    else MovieDetails(
        id = id!!,
        title = title!!,
        overview = overview!!,
        vote = voteAverage!!,
        votersCount = voteCount!!,
        posterUrl = "$IMAGES_BASE_URL$posterPath",
        status = status!!,
        genres = genres!!.mapNotNull { it.validateGenre() },
        productionCompanies = productionCompanies!!.mapNotNull { it.validateCompanies() }
    )


}

fun MovieDetailsDto.Genre?.validateGenre(): MovieGenres? {
    val genreName = this?.name
    return if (!genreName.isNullOrBlank()) MovieGenres(genreName)
    else null
}

fun MovieDetailsDto.ProductionCompany?.validateCompanies(): ProductionCompanies? {
    val name = this?.name
    val logo = this?.logoPath
    return if (name.isNullOrBlank() || logo.isNullOrBlank()) null
    else ProductionCompanies(logoPath = logo, name = name)
}

