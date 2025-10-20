package com.example.app_bancario_teste.data.repository

import com.example.app_bancario_teste.data.local.dao.PaymentDao
import com.example.app_bancario_teste.data.local.entity.PaymentEntity
import com.example.app_bancario_teste.data.remote.service.PaymentsService
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.domain.repository.PaymentRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class PaymentsRepositoryImplTest {
    @Mock
    lateinit var paymentsService: PaymentsService

    @Mock
    lateinit var paymentDao: PaymentDao

    lateinit var paymentRepository: PaymentRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        paymentRepository =
            PaymentsRepositoryImpl(paymentDao = paymentDao, paymentsService = paymentsService)
    }

    @Test
    fun `getPayment should recover data from paid accounts`() = runTest {
        val mockListPayment = listOf(
            AccountPayment("12000", "1", "12/12/12"),
            AccountPayment("800", "2", "12/12/12"),
            AccountPayment("200", "3", "12/12/12"),
            AccountPayment("200", "4", "12/12/12")
        )
        val mockEntityList = mockListPayment.map { PaymentEntity(it) }.toTypedArray()

        Mockito.`when`(paymentsService.getPayments()).thenReturn(
            Response.success(mockListPayment)
        )
        Mockito.`when`(paymentDao.savePayment(*mockEntityList)).thenReturn(listOf(1L,2L,3L,4L,5L))

        val result = paymentRepository.getPayments()

        assertThat(result).isNotEmpty()
        assertThat(result).hasSize(4)
        assertThat(result.first().electricityBill).isEqualTo("12000")
        Mockito.verify(paymentDao).savePayment(*mockEntityList)

    }


}