package com.adel.presentation.screens.details

import com.adel.models.ErrorMessage
import com.adel.models.MovieDetails
import com.adel.models.values.MovieId


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

