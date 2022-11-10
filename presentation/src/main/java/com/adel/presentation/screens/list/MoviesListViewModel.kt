package com.adel.presentation.screens.list


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.adel.bussiness.mapper.toMovie
import com.adel.bussiness.repositories.MoviesRepository
import com.adel.presentation.screens.list.ListScreenAction.FetchMovies
import com.adel.presentation.screens.list.ListScreenAction.LoadMore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    var listScreenState by mutableStateOf<ListScreenState>(ListScreenState.Success())
        private set

    private val _effects = MutableSharedFlow<com.adel.data.models.ErrorMessage?>()
    val effects: SharedFlow<com.adel.data.models.ErrorMessage?> = _effects

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
        listScreenState = when (val response = moviesRepo.getMoviesList(
            com.adel.data.models.CurrentPage(
                1
            )
        )) {
            is Either.Left -> {
                _effects.emit(response.value.message)
                ListScreenState.Error(response.value.message)
            }
            is Either.Right -> ListScreenState.Success(
                movies = response.value.list.mapNotNull { it.toMovie() },
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

                val nextPage = com.adel.data.models.CurrentPage(currentState.currentPage.value + 1)
                when (val response = moviesRepo.getMoviesList(nextPage)) {

                    is Either.Left -> {
                        _effects.emit(response.value.message)
                        listScreenState = currentState.copy(isLoading = false)
                    }

                    is Either.Right -> {
                        val receivedList = response.value.list.mapNotNull { it.toMovie() }
                        val newList = currentState.movies.toMutableList().plus(receivedList)

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
