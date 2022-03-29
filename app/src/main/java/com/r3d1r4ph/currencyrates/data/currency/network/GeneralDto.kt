package com.r3d1r4ph.currencyrates.data.currency.network

import com.r3d1r4ph.currencyrates.data.currency.local.CurrencyEntity
import com.r3d1r4ph.currencyrates.data.currency.local.RelevanceEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeneralDto(
    @SerialName("Timestamp")
    val timestamp: String,
    @SerialName("Valute")
    val currencyList: Map<String, CurrencyDto>
) {
    fun toRelevanceEntity() =
        RelevanceEntity(
            timestamp = timestamp
        )

    fun toCurrencyEntityList(relevanceId: Long): List<CurrencyEntity> =
        currencyList.values.map { it.toEntity(relevanceId) }

}