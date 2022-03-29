package com.r3d1r4ph.currencyrates.data.currency

import com.r3d1r4ph.currencyrates.domain.General
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneralDto(
    @SerialName("Timestamp")
    val timestamp: String,
    @SerialName("Valute")
    val currencyList: Map<String, CurrencyDto>
) {
    fun toDomain(): General =
        General(
            timestamp = timestamp,
            currencyList = currencyList.values.map { it.toDomain() }
        )
}