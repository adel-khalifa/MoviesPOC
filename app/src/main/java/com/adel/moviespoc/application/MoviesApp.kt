package com.adel.moviespoc.application

import android.app.Application
import com.adel.moviespoc.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(androidContext = applicationContext)
            modules(
                listOf(
                    networkModule
                )
            )

        }
    }
}