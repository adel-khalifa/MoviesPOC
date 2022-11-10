package com.adel.moviespoc.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.adel.moviespoc.R
import com.adel.moviespoc.domain.entities.Movie


@Composable
fun RemoteImage(
    modifier: Modifier,
    url: String,
) {
    Image(
        modifier = modifier,
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current).data(data = url)
                .apply(block = fun ImageRequest.Builder.() {
                    error(R.drawable.warning_error_svgrepo_com)
                    this.crossfade(1000)
                }).build()
        ),
        contentDescription = "",
        contentScale = ContentScale.Crop
    )
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
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(percent = 8))
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

