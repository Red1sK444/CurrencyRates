package com.r3d1r4ph.currencyrates.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.r3d1r4ph.currencyrates.data.currency.local.CurrencyDao
import com.r3d1r4ph.currencyrates.data.currency.local.CurrencyEntity
import com.r3d1r4ph.currencyrates.data.currency.local.RelevanceDao
import com.r3d1r4ph.currencyrates.data.currency.local.RelevanceEntity

@Database(
    entities = [CurrencyEntity::class, RelevanceEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyDao
    abstract fun getRelevanceDao(): RelevanceDao
}