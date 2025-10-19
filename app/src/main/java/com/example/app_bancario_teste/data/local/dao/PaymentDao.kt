package com.example.app_bancario_teste.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.app_bancario_teste.data.local.entity.PaymentEntity

@Dao
interface PaymentDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun savePayment(vararg paymentEntity: PaymentEntity)

  @Query(value = "SELECT *FROM payment")
  suspend fun fetchAll(): List<PaymentEntity>

}