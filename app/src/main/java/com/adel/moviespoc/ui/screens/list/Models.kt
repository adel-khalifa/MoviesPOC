package com.adel.moviespoc.ui.screens.list

import com.adel.moviespoc.data.models.CurrentPage
import com.adel.moviespoc.data.models.ErrorMessage
import com.adel.moviespoc.data.models.TotalPages
import com.adel.moviespoc.domain.entities.Movie

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

