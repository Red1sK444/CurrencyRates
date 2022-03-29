package com.r3d1r4ph.currencyrates.data.currency

import com.r3d1r4ph.currencyrates.domain.General
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getGeneralInfo(): Flow<General>
    suspend fun getCurrencyList(): Result<Boolean>
}