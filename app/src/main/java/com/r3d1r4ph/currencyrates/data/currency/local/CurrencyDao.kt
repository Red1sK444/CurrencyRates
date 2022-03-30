package com.r3d1r4ph.currencyrates.data.currency.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyEntities: List<CurrencyEntity>)

    @Query("SELECT * FROM currencies WHERE id=:id")
    suspend fun getCurrencyById(id: String): CurrencyEntity
}