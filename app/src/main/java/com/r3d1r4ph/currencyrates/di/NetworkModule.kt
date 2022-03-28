package com.r3d1r4ph.currencyrates.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.r3d1r4ph.currencyrates.utils.interceptors.NetworkConnectionInterceptor
import com.r3d1r4ph.currencyrates.utils.interceptors.StatusCodeInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideClient(
        @ApplicationContext appContext: Context
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .addInterceptor(NetworkConnectionInterceptor(appContext))
        .addInterceptor(StatusCodeInterceptor())
        .build()

    @Provides
    fun provideBaseUrl(): String =
        "https://www.cbr-xml-daily.ru/"

    @Provides
    fun provideMediaType(): MediaType =
        "application/json".toMediaType()

    @Provides
    fun provideJson(): Json =
        Json { ignoreUnknownKeys = true }

    @ExperimentalSerializationApi
    @Provides
    fun providesConverterFactory(
        json: Json,
        mediaType: MediaType
    ): Converter.Factory = json.asConverterFactory(mediaType)


    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        baseUrl: String,
        converterFactory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .build()
}