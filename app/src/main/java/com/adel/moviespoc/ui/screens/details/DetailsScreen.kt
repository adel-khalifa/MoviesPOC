package com.adel.moviespoc.ui.screens.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adel.moviespoc.R
import com.adel.moviespoc.data.models.MovieId
import com.adel.moviespoc.ui.components.LoadingComponent
import com.adel.moviespoc.ui.components.RemoteImage
import com.adel.moviespoc.ui.screens.list.RenderErrorState
import com.adel.moviespoc.ui.screens.util.extractErrorMessage
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
                    viewModel.dispatch(DetailsScreenAction.LoadMovieDetails(MovieId(movieId)))
                }

                is DetailsScreenState.Success -> RenderMovieDetails(
                    uiState = uiState,
                    onBackPressed = { onBackPressed() },
                    onRefresh = {
                        viewModel.dispatch(DetailsScreenAction.LoadMovieDetails(MovieId(movieId)))
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

@Composable
fun RateSection(vote: Double) {
    Row(
        Modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically

    ) {


        Image(
            modifier = Modifier
                .wrapContentSize()
                .size(20.dp),
            painter = painterResource(id = R.drawable.round_star_rate_24),
            colorFilter = ColorFilter.tint(Color.Yellow),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(2.dp))
        CustomText(
            text = "${vote.toInt()}/10", color = MaterialTheme.colors.onBackground,
            textSize = 10.sp
        )

    }
}

@Composable
fun TextChip(text: String) {
    Button(
        modifier = Modifier.height(32.dp),
        shape = RoundedCornerShape(corner = CornerSize(18.dp)),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        border = BorderStroke(.5.dp, MaterialTheme.colors.primary),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        onClick = { }
    ) {
        CustomText(text = text, textSize = 14.sp)

    }
}

@Composable
fun CustomText(
    text: String,
    color: Color? = null,
    textSize: TextUnit,
    maxLines: Int = 1
) {
    Text(
        text = text,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.wrapContentWidth(Alignment.Start),
        style = MaterialTheme.typography.h6.copy(
            color = color ?: MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = textSize
        )
    )
}

@Composable
fun ExpandableText(text: String, color: Color? = null) {
    var isExpanded by remember { mutableStateOf(false) }

    Text(
        text = text,
        maxLines = if (isExpanded) 24 else 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .animateContentSize()
            .clickable { isExpanded = !isExpanded },
        style = MaterialTheme.typography.body2.copy(
            color = color ?: MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.SemiBold,
        )
    )
    AnimatedVisibility(!isExpanded) {
        CustomText(text = stringResource(id = R.string.show_more), textSize = 12.sp)
    }


}

