package com.adel.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adel.models.values.MovieId
import com.adel.presentation.R
import com.adel.presentation.components.*
import com.adel.presentation.screens.list.RenderErrorState
import com.adel.presentation.screens.util.extractErrorMessage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel


@Composable
fun DetailsScreen(
    movieId: Int,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: DetailsViewModel = koinViewModel(),
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = movieId) {
        viewModel.dispatch(DetailsScreenAction.LoadMovieDetails(MovieId(movieId)))
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { message ->
            scaffoldState.snackbarHostState.showSnackbar(
                message = context.extractErrorMessage(message),
                actionLabel = "OK"
            )
        }
    }

    Scaffold(scaffoldState = scaffoldState) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            when (val uiState = viewModel.detailsState) {
                is DetailsScreenState.Error -> RenderErrorState(uiState.message) {
                    viewModel.dispatch(
                        DetailsScreenAction.LoadMovieDetails(
                            MovieId(
                                movieId
                            )
                        )
                    )
                }

                is DetailsScreenState.Success -> RenderMovieDetails(
                    uiState = uiState,
                    onBackPressed = { onBackPressed() },
                    onRefresh = {
                        viewModel.dispatch(
                            DetailsScreenAction.LoadMovieDetails(
                                MovieId(
                                    movieId
                                )
                            )
                        )
                    })
            }
        }
    }

}

@Composable
fun RenderMovieDetails(
    uiState: DetailsScreenState.Success,
    onBackPressed: () -> Unit,
    onRefresh: () -> Unit,
) {
    uiState.movieDetails?.let { movie ->


        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = uiState.isLoading),
            onRefresh = { onRefresh() }) {
            Column() {

                LoadingComponent(isLoading = uiState.isLoading)
                Box {
                    Column(Modifier.verticalScroll(rememberScrollState())) {


                        RemoteImage(
                            Modifier
                                .fillMaxWidth()
                                .height(500.dp), url = movie.posterUrl
                        )

                        Column(Modifier.padding(12.dp)) {


                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.h5,
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(6.dp))
                            RateSection(movie.vote)
                            CustomText(text = movie.status, textSize = 14.sp, color = Color.Green)
                            Spacer(modifier = Modifier.width(24.dp))
                            CustomText(
                                text = stringResource(id = R.string.overview),
                                textSize = 24.sp
                            )
                            ExpandableText(text = movie.overview)
                            Spacer(modifier = Modifier.height(16.dp))

                            CustomText(
                                text = stringResource(id = R.string.genres),
                                textSize = 16.sp
                            )
                            Row {
                                movie.genres.forEach {
                                    TextChip(it.name)
                                    Spacer(modifier = Modifier.width(4.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))


                        }

                    }
                    Image(
                        painter = painterResource(id = R.drawable.left_arrow_circle_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onBackPressed() }
                            .padding(12.dp)
                    )
                }


            }
        }

    }


}
