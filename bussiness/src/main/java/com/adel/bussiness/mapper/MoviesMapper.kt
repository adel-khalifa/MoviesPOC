package com.adel.bussiness.mapper

import com.adel.data.models.MovieDetailsDto
import com.adel.data.models.MovieDto
import com.adel.models.entities.Movie

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


fun MovieDetailsDto.toMovieDetails(): com.adel.models.entities.MovieDetails? {
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
    else com.adel.models.entities.MovieDetails(
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

fun MovieDetailsDto.Genre?.validateGenre(): com.adel.models.entities.MovieGenres? {
    val genreName = this?.name
    return if (!genreName.isNullOrBlank()) com.adel.models.entities.MovieGenres(genreName)
    else null
}

fun MovieDetailsDto.ProductionCompany?.validateCompanies(): com.adel.models.entities.ProductionCompanies? {
    val name = this?.name
    val logo = this?.logoPath
    return if (name.isNullOrBlank() || logo.isNullOrBlank()) null
    else com.adel.models.entities.ProductionCompanies(logoPath = logo, name = name)
}

