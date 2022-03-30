package com.r3d1r4ph.currencyrates.data.currency

import com.r3d1r4ph.currencyrates.domain.Currency
import com.r3d1r4ph.currencyrates.domain.General
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getGeneralInfo(): Flow<General>
    suspend fun loadCurrencyList(): Result<Boolean>
    suspend fun getCurrencyById(id: String): Currency
}