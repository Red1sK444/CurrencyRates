package com.r3d1r4ph.currencyrates.data.currency

import com.r3d1r4ph.currencyrates.domain.General
import com.r3d1r4ph.currencyrates.utils.exceptions.EmptyResponseException
import com.r3d1r4ph.currencyrates.utils.safeApiCall
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : CurrencyRepository {

    override suspend fun getCurrencyList(): Result<General> {
        return safeApiCall(
            apiCall = {
                currencyService.getCurrencyList()
            },
            onSuccess = { response ->
                when (val result = response.body()?.toDomain()) {
                    null -> Result.failure(EmptyResponseException())
                    else -> Result.success(result)
                }
            }
        )

    }
}