package com.r3d1r4ph.currencyrates.data.currency

import com.r3d1r4ph.currencyrates.domain.General

interface CurrencyRepository {

    suspend fun getCurrencyList(): Result<General>
}