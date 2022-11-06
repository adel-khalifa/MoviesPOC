package com.adel.moviespoc.di

import com.adel.moviespoc.BuildConfig
import com.adel.moviespoc.data.interceptors.KeyInterceptor
import com.adel.moviespoc.data.services.MoviesService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module {
    single { moshi() }
    single { okHttpClient(get()) }
    single { KeyInterceptor() }
    single { retrofit(BuildConfig.SERVER_URL, get(), get()) }
    single { createMovieService(get()) }


}


fun retrofit(baseUrl: String, okHtpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHtpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

private fun okHttpClient(keyInterceptor: KeyInterceptor): OkHttpClient {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    return OkHttpClient.Builder().addInterceptor(logging).addInterceptor(keyInterceptor).build()
}

private fun moshi(): Moshi {
    return Moshi.Builder().build()
}


fun createMovieService(retrofit: Retrofit): MoviesService {
    return retrofit.create(MoviesService::class.java)
}