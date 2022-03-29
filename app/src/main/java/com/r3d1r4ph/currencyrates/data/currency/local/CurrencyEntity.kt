package com.r3d1r4ph.currencyrates.data.currency.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.r3d1r4ph.currencyrates.domain.Currency

@Entity(
    tableName = "currencies",
    foreignKeys = [
        ForeignKey(
            entity = RelevanceEntity::class,
            parentColumns = ["id"],
            childColumns = ["relevance"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nominal: Int,
    val charCode: String,
    val name: String,
    val value: Float,
    @ColumnInfo(index = true)
    val relevance: Long
) {
    fun toDomain(): Currency =
        Currency(
            id = id,
            nominal = nominal,
            charCode = charCode,
            name = name,
            value = value
        )
}