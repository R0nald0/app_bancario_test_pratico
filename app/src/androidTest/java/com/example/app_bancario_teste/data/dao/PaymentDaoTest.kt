package com.example.app_bancario_teste.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.app_bancario_teste.data.local.dao.AppDatabase
import com.example.app_bancario_teste.data.local.dao.PaymentDao
import com.example.app_bancario_teste.data.local.entity.PaymentEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


class PaymentDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var paymentDao: PaymentDao

    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        paymentDao = appDatabase.paymentDao()
    }


    @Test
    fun savePayment_should_save_a_payment() = runTest {
        val mockListPayment = listOf(
            PaymentEntity(1, "1", "12/12/12"),
            PaymentEntity(2, "2", "12/12/12"),
            PaymentEntity(3, "3", "12/12/12"),
            PaymentEntity(4, "4", "12/12/12")
        )
        val result = paymentDao.savePayment(*mockListPayment.toTypedArray())
        assertThat(result).isNotEmpty()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}