@file:OptIn(ExperimentalFoundationApi::class)

package com.adel.moviespoc.ui.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adel.moviespoc.data.models.ErrorMessage
import com.adel.moviespoc.domain.entities.Movie
import com.adel.moviespoc.ui.components.ErrorView
import com.adel.moviespoc.ui.components.LoadingComponent
import com.adel.moviespoc.ui.components.PaginatedLazyColumn
import com.adel.moviespoc.ui.components.RemoteImage
import com.adel.moviespoc.ui.screens.details.CustomText
import com.adel.moviespoc.ui.screens.details.RateSection
import com.adel.moviespoc.ui.screens.util.extractErrorMessage
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
fun RenderErrorState(message: ErrorMessage?, onTryAgain: () -> Unit) {
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
                contentPadding = PaddingValues(0.dp),
                canLoadMore = uiState.currentPage.value < uiState.totalPages.value,
                content = { movie ->
                    MovieItem(movie = movie) { onNavigateToDetails(movie.id) }
                }
            )

        }
    }

}

@Composable
fun MovieItem(modifier: Modifier = Modifier, movie: Movie, onMovieClicked: (id: Int) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMovieClicked(movie.id) }
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.Top
    ) {

        RemoteImage(
            url = movie.posterUrl,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            CustomText(text = movie.title, textSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))

            CustomText(text = movie.overview, textSize = 12.sp, maxLines = 2)
            RateSection(vote = movie.vote)

        }
    }
}