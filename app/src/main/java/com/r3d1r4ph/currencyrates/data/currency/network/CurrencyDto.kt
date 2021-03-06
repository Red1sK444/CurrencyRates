package com.r3d1r4ph.currencyrates.data.currency.network

import com.r3d1r4ph.currencyrates.data.currency.local.CurrencyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    @SerialName("ID")
    val id: String,
    @SerialName("Nominal")
    val nominal: Int,
    @SerialName("CharCode")
    val charCode: String,
    @SerialName("Name")
    val name: String,
    @SerialName("Value")
    val value: Float
) {
    fun toEntity(relevanceId: Long) =
        CurrencyEntity(
            id = id,
            name = name,
            nominal = nominal,
            charCode = charCode,
            value = value,
            relevance = relevanceId
        )
}
