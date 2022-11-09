package com.adel.moviespoc.ui.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.adel.moviespoc.data.models.ErrorMessage
import com.adel.moviespoc.data.models.MovieId
import com.adel.moviespoc.domain.repositories.MoviesRepository
import com.adel.moviespoc.ui.mapper.toMovieDetails
import com.adel.moviespoc.ui.screens.details.DetailsScreenState.Error
import com.adel.moviespoc.ui.screens.details.DetailsScreenState.Success
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val moviesRepo: MoviesRepository) : ViewModel() {


    var detailsState by mutableStateOf<DetailsScreenState>(Success())
        private set

    private val _effects = MutableSharedFlow<ErrorMessage?>()
    val effects: MutableSharedFlow<ErrorMessage?> = _effects


    // just one exposed API to UI-Component/Controller
    fun dispatch(action: DetailsScreenAction) {
        when (action) {
            is DetailsScreenAction.LoadMovieDetails -> getMovieDetails(action.movieId)
        }
    }


    private fun getMovieDetails(id: MovieId) = viewModelScope.launch {
        val currentState = detailsState
        showLoading(currentState)

        when (val response = moviesRepo.getMovieById(id)) {

            is Either.Right -> setState(Success(response.value.toMovieDetails()))


            is Either.Left -> when (currentState) {
                is Success -> {
                    if (currentState.movieDetails == null) {
                        setState(Error(response.value.message))
                    } else {
                        // if details already rendered, therefore don't change the state and just emit a message
                        _effects.emit(response.value.message)
                    }
                }
                is Error -> {
                    setState(Error(response.value.message))
                    _effects.emit(response.value.message)
                }


            }


        }
        hideLoading(detailsState)
    }

    private fun showLoading(currentState: DetailsScreenState) {
        if (currentState is Success) setState(currentState.copy(isLoading = true))
        else setState(Success(isLoading = true))
    }


    private fun hideLoading(currentState: DetailsScreenState) {
        if (currentState is Success) setState(currentState.copy(isLoading = false))
        else return
    }


    fun setState(state: DetailsScreenState) {
        detailsState = state
    }

}