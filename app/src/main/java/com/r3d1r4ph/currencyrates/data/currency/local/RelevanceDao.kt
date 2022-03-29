package com.r3d1r4ph.currencyrates.data.currency.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RelevanceDao {

    @Query("SELECT * FROM relevance")
    fun getGeneralInfo(): Flow<RelevanceWithCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelevance(relevanceEntity: RelevanceEntity): Long

    @Query("DELETE FROM relevance")
    suspend fun deleteRelevance()
}