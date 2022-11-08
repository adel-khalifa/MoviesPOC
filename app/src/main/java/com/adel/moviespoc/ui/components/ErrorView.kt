package com.adel.moviespoc.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
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
fun ErrorView(
    title: String = stringResource(id = R.string.error_occurred),
    onTryAgain: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.warning_error_svgrepo_com),
            contentDescription = "",
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        if (title.isNotBlank()) {
            Text(
                text = title,
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp

                )
            )
        }
        Button(
            modifier = Modifier.height(40.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = { onTryAgain() }
        ) {
            Text(
                text = stringResource(id = R.string.try_again),
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp

                )
            )
        }

    }
}
