package com.r3d1r4ph.currencyrates.data.currency.local

import androidx.room.Embedded
import androidx.room.Relation
import com.r3d1r4ph.currencyrates.domain.General

data class RelevanceWithCurrency(
    @Embedded val relevance: RelevanceEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "relevance"
    )
    val currencies: List<CurrencyEntity>
) {
    fun toDomain(): General =
        General(
            relevance = relevance.toDomain(),
            currencyList = currencies.map { it.toDomain() }
        )
}
