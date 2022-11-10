package com.adel.presentation.screens.details


sealed class DetailsScreenState {
    data class Success(
        val movieDetails: com.adel.models.entities.MovieDetails? = null,
        val isLoading: Boolean = true
    ) : DetailsScreenState()

    data class Error(val message: com.adel.data.models.ErrorMessage?) : DetailsScreenState()

}


sealed class DetailsScreenAction {
    data class LoadMovieDetails(val movieId: com.adel.data.models.MovieId) : DetailsScreenAction()
}

