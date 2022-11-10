package com.adel.presentation.screens.list

import com.adel.data.models.CurrentPage
import com.adel.data.models.ErrorMessage
import com.adel.data.models.TotalPages

sealed class ListScreenState() {
    data class Success(
        val movies: List<com.adel.models.entities.Movie> = emptyList(),
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

