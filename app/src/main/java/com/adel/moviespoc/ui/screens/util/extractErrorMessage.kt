package com.adel.moviespoc.ui.screens.util

import android.content.Context
import com.adel.moviespoc.R
import com.adel.moviespoc.data.models.ErrorMessage

fun Context.extractErrorMessage(failure: ErrorMessage?): String {
    val message = when (failure) {
        is ErrorMessage.LocalMessage -> getString(failure.messageRes)
        is ErrorMessage.ServerMessage -> failure.message
        else -> getString(R.string.error_general)
    }
    return message.takeIf { it.isNotBlank() } ?: getString(R.string.error_general)

}