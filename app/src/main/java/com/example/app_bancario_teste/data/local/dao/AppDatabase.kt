package com.example.app_bancario_teste.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app_bancario_teste.data.local.entity.PaymentEntity


@Database(
    entities = [PaymentEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paymentDao(): PaymentDao
}