package com.adel.presentation.screens.list

import com.adel.models.ErrorMessage
import com.adel.models.Movie
import com.adel.models.values.CurrentPage
import com.adel.models.values.TotalPages

sealed class ListScreenState() {
    data class Success(
        val movies: List<Movie> = emptyList(),
        val currentPage: CurrentPage = CurrentPage(1),
        val totalPages: TotalPages = TotalPages(1),
        val isLoading: Boolean = true
    ) : ListScreenState()

    data class Error(val message: ErrorMessage?) : ListScreenState()

}


sealed class ListScreenAction {
    object FetchMovies : ListScreenAction()
    object LoadMore : ListScreenAction()
}

