package com.r3d1r4ph.currencyrates.utils

import com.r3d1r4ph.currencyrates.utils.exceptions.UnknownException
import retrofit2.Response

suspend fun <DTO, DOMAIN> safeApiCall(
    apiCall: suspend () -> Response<DTO>,
    onSuccess: (Response<DTO>) -> Result<DOMAIN>
): Result<DOMAIN> {
    try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            return onSuccess.invoke(response)
        }
    } catch (e: Exception) {
        return Result.failure(e)
    }
    return Result.failure(UnknownException())
}