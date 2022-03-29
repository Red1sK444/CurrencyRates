package com.r3d1r4ph.currencyrates.di

import android.content.Context
import androidx.room.Room
import com.r3d1r4ph.currencyrates.data.AppDatabase
import com.r3d1r4ph.currencyrates.data.currency.local.CurrencyDao
import com.r3d1r4ph.currencyrates.data.currency.local.RelevanceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideRelevanceDao(
        appDatabase: AppDatabase
    ): RelevanceDao = appDatabase.getRelevanceDao()

    @Provides
    fun provideCurrencyDao(
        appDatabase: AppDatabase
    ): CurrencyDao = appDatabase.getCurrencyDao()

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
}