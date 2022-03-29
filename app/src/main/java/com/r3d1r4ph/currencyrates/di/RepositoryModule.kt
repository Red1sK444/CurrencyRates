package com.r3d1r4ph.currencyrates.di

import com.r3d1r4ph.currencyrates.data.currency.CurrencyRepository
import com.r3d1r4ph.currencyrates.data.currency.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl
    ): CurrencyRepository
}