package com.adel.moviespoc.ui.screens.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.adel.moviespoc.data.models.CurrentPage
import com.adel.moviespoc.data.models.ErrorMessage
import com.adel.moviespoc.domain.repositories.MoviesRepository
import com.adel.moviespoc.ui.screens.list.ListScreenAction.FetchMovies
import com.adel.moviespoc.ui.screens.list.ListScreenAction.LoadMore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    var listScreenState by mutableStateOf<ListScreenState>(ListScreenState.Success())

    private val _effects = MutableSharedFlow<ErrorMessage?>()
    val effects: SharedFlow<ErrorMessage?> = _effects

    init {
        dispatch(FetchMovies)
    }

    fun dispatch(action: ListScreenAction) {
        when (action) {
            FetchMovies -> fetchMovies()
            LoadMore -> loadMoreMovies()
        }
    }

    private fun fetchMovies() = viewModelScope.launch {
        listScreenState = when (val response = moviesRepo.getMoviesList(CurrentPage(1))) {
            is Either.Left -> {
                _effects.emit(response.value.message)
                ListScreenState.Error(response.value.message)
            }
            is Either.Right -> ListScreenState.Success(
                movies = response.value.list,
                currentPage = response.value.page,
                totalPages = response.value.totalPages,
                isLoading = false
            )
        }
    }


    private fun loadMoreMovies() {
        val currentState = listScreenState
        if (currentState is ListScreenState.Success) {


            viewModelScope.launch {
                listScreenState = currentState.copy(isLoading = true)
                val nextPage = CurrentPage(currentState.currentPage.value + 1)
                when (val response = moviesRepo.getMoviesList(nextPage)) {
                    is Either.Left -> _effects.emit(response.value.message)

                    is Either.Right -> {
                        val newList = currentState.movies.toMutableList().plus(response.value.list)
                        listScreenState = currentState.copy(
                            movies = newList.toList(),
                            currentPage = response.value.page,
                            totalPages = response.value.totalPages,
                            isLoading = false
                        )
                    }

                }
            }
        }
    }
}
