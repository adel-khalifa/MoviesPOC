package com.adel.moviespoc.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.adel.moviespoc.R


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