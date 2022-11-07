package com.adel.moviespoc.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.adel.moviespoc.domain.models.Movie
import com.adel.moviespoc.domain.repositories.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {
    var loginStateFlow by mutableStateOf<List<Movie>>(emptyList())


    init {
        getMoviesList()
    }

    private fun getMoviesList() {


        viewModelScope.launch {

            when (val response = moviesRepo.getMoviesList()) {

                is Either.Left -> {
                    Log.i("ViewModelLogger", response.toString())

                }
                is Either.Right -> {
                    loginStateFlow = response.value
                }

            }

        }
    }
}