package com.adel.moviespoc.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adel.moviespoc.R

@Composable
fun EmptyView(title: String = stringResource(id = R.string.no_result)) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.binoculars_svgrepo_com),
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(top = 32.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp

            )
        )
    }
}