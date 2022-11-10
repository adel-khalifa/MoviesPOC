package com.adel.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun LoadingComponent(isLoading: Boolean) {
    AnimatedVisibility(visible = isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), color = Color.Green)
    }
}
