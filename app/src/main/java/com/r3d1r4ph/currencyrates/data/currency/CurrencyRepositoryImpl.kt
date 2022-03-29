package com.r3d1r4ph.currencyrates.data.currency

import com.r3d1r4ph.currencyrates.domain.General
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRepository {

    override suspend fun getCurrencyList(): General? {
        val response = currencyService.getCurrencyList()
        return if (response.isSuccessful) {
            response.body()?.toDomain()
        } else {
            null
        }
    }
}