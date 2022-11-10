package com.adel.moviespoc.di

import com.adel.bussiness.repositories.MoviesRepository
import com.adel.bussiness.repositories.MoviesRepositoryImpl
import com.adel.data.BuildConfig
import com.adel.presentation.screens.details.DetailsViewModel
import com.adel.presentation.screens.list.MoviesListViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module {
    single { moshi() }
    single { okHttpClient(get()) }
    single { com.adel.data.interceptors.KeyInterceptor() }
    single { retrofit(BuildConfig.SERVER_URL, get(), get()) }
    single { createMovieService(get()) }

    single<com.adel.data.source.interfaces.MoviesListDataSource> {
        com.adel.data.source.implementation.MoviesListDataSourceImpl(
            get()
        )
    }
    single<com.adel.data.source.interfaces.MoviesDetailsDataSource> {
        com.adel.data.source.implementation.MoviesDetailsDataSourceImpl(
            get()
        )
    }
    single<MoviesRepository> {
        MoviesRepositoryImpl(
            get(),
            get()
        )
    }

    viewModelOf(::MoviesListViewModel)
    viewModelOf(::DetailsViewModel)


}


fun retrofit(baseUrl: String, okHtpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHtpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

private fun okHttpClient(keyInterceptor: com.adel.data.interceptors.KeyInterceptor): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder().addInterceptor(logging).addInterceptor(keyInterceptor).build()
}

private fun moshi(): Moshi {
    return Moshi.Builder().build()
}


fun createMovieService(retrofit: Retrofit): com.adel.data.services.MoviesService {
    return retrofit.create(com.adel.data.services.MoviesService::class.java)
}