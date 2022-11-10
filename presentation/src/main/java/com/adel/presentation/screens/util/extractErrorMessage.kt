package com.adel.presentation.screens.util

import android.content.Context
import com.adel.models.ErrorMessage
import com.adel.presentation.R

fun Context.extractErrorMessage(failure: ErrorMessage?): String {
    val message = when (failure) {
        is ErrorMessage.LocalMessage -> getString(failure.messageRes)
        is ErrorMessage.ServerMessage -> failure.message
        else -> getString(R.string.error_general)
    }
    return message.takeIf { it.isNotBlank() } ?: getString(R.string.error_general)

}