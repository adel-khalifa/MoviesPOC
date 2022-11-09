package com.adel.moviespoc.ui.mapper

import com.adel.moviespoc.data.models.MovieDetailsDto
import com.adel.moviespoc.data.models.MovieDto
import com.adel.moviespoc.domain.entities.Movie
import com.adel.moviespoc.domain.entities.MovieDetails
import com.adel.moviespoc.domain.entities.MovieGenres
import com.adel.moviespoc.domain.entities.ProductionCompanies

private const val IMAGES_BASE_URL = "https://image.tmdb.org/t/p/original/"

fun MovieDto.toMovie(): Movie? {
    return if (
        id == null ||
        title == null ||
        overview == null ||
        voteAverage == null ||
        posterPath == null ||
        voteCount == null
    ) null
    else Movie(
        id = id,
        title = title,
        posterUrl = "$IMAGES_BASE_URL$posterPath",
        overview = overview,
        vote = voteAverage,
        votersCount = voteCount
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
        id = id,
        title = title,
        overview = overview,
        vote = voteAverage,
        votersCount = voteCount,
        posterUrl = "$IMAGES_BASE_URL$posterPath",
        status = status,
        genres = genres.mapNotNull { it.validateGenre() },
        productionCompanies = productionCompanies.mapNotNull { it.validateCompanies() }
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

