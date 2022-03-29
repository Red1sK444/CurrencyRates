package com.r3d1r4ph.currencyrates.data.currency.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.r3d1r4ph.currencyrates.domain.Relevance

@Entity(tableName = "relevance")
data class RelevanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: String
) {
    fun toDomain(): Relevance =
        Relevance(
            timestamp = timestamp
        )
}