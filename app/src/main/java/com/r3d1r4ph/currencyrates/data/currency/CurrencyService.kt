package com.r3d1r4ph.currencyrates.data.currency

import retrofit2.Response
import retrofit2.http.GET

interface CurrencyService {

    @GET("daily_json.js")
    suspend fun getCurrencyList(): Response<GeneralDto>
}