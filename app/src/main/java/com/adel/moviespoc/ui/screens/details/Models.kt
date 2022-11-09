package com.adel.moviespoc.ui.screens.details

import com.adel.moviespoc.data.models.ErrorMessage
import com.adel.moviespoc.data.models.MovieId
import com.adel.moviespoc.domain.entities.MovieDetails


sealed class DetailsScreenState {
    data class Success(
        val movieDetails: MovieDetails? = null,
        val isLoading: Boolean = true
    ) : DetailsScreenState()

    data class Error(val message: ErrorMessage?) : DetailsScreenState()

}


sealed class DetailsScreenAction {
    data class LoadMovieDetails(val movieId: MovieId) : DetailsScreenAction()
}

