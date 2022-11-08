package com.adel.moviespoc.ui.screens.list

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.adel.moviespoc.R
import com.adel.moviespoc.data.models.ErrorMessage
import com.adel.moviespoc.ui.components.ErrorView
import com.adel.moviespoc.ui.components.PaginatedLazyColumn
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListScreen(
    listViewModel: MoviesListViewModel = koinViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigateToDetails: (id: Int) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(scaffoldState.snackbarHostState) {
        listViewModel.effects.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.extractErrorMessage(message),
                actionLabel = "OK"
            )
        }
    }

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (val uiState = listViewModel.listScreenState) {
                is ListScreenState.Error -> RenderErrorState(uiState.message) {
                    listViewModel.dispatch(ListScreenAction.FetchMovies)
                }
                is ListScreenState.Success -> RenderSuccessState(
                    uiState,
                    onNavigateToDetails = onNavigateToDetails,
                    onLoadMore = { listViewModel.dispatch(ListScreenAction.LoadMore) }
                )
            }
        }
    }


}

@Composable
fun RenderErrorState(message: ErrorMessage?, onTryAgain: () -> Unit) {
    ErrorView(LocalContext.current.extractErrorMessage(message), onTryAgain = onTryAgain)
}


@Composable
fun RenderSuccessState(
    uiState: ListScreenState.Success,
    onNavigateToDetails: (id: Int) -> Unit,
    onLoadMore: () -> Unit
) {
    val listState: LazyListState = rememberLazyListState()
    Column {

        AnimatedVisibility(visible = uiState.isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color.Green)
        }

        uiState.movies.PaginatedLazyColumn(
            modifier = Modifier,
            onLoadMore = onLoadMore,
            listState = listState,
            contentPadding = PaddingValues(0.dp),
            canLoadMore = uiState.currentPage.value < uiState.totalPages.value,
            content = { movie ->
                Button(onClick = { onNavigateToDetails(movie.id) }) {
                    Text(text = movie.title)
                }
            }
        )

    }

}

fun Context.extractErrorMessage(failure: ErrorMessage?): String = when (failure) {
    is ErrorMessage.LocalMessage -> getString(failure.messageRes)
    is ErrorMessage.ServerMessage -> failure.message
    else -> getString(R.string.error_general)
}