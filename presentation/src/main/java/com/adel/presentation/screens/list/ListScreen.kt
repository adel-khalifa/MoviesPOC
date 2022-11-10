package com.adel.presentation.screens.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.adel.presentation.components.ErrorView
import com.adel.presentation.components.LoadingComponent
import com.adel.presentation.components.MovieItem
import com.adel.presentation.components.PaginatedLazyColumn
import com.adel.presentation.screens.util.extractErrorMessage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListScreen(
    listViewModel: MoviesListViewModel = koinViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigateToDetails: (id: Int) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
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
                    onRefresh = { listViewModel.dispatch(ListScreenAction.FetchMovies) },
                    onLoadMore = { listViewModel.dispatch(ListScreenAction.LoadMore) }
                )
            }
        }
    }


}

@Composable
fun RenderErrorState(message: com.adel.data.models.ErrorMessage?, onTryAgain: () -> Unit) {
    ErrorView(LocalContext.current.extractErrorMessage(message), onTryAgain = onTryAgain)
}


@Composable
fun RenderSuccessState(
    uiState: ListScreenState.Success,
    onNavigateToDetails: (id: Int) -> Unit,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit
) {
    val listState: LazyListState = rememberLazyListState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = uiState.isLoading),
        onRefresh = { onRefresh() }) {
        Column {
            LoadingComponent(isLoading = uiState.isLoading)
            uiState.movies.PaginatedLazyColumn(
                modifier = Modifier,
                onLoadMore = onLoadMore,
                listState = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(12.dp),
                canLoadMore = uiState.currentPage.value < uiState.totalPages.value,
                content = { movie ->
                    MovieItem(movie = movie) { onNavigateToDetails(movie.id) }
                }
            )

        }
    }

}
