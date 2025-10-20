package com.example.app_bancario_teste.data.repository

import android.util.Log
import com.example.app_bancario_teste.core.exception.RepositoryException
import com.example.app_bancario_teste.data.local.dao.PaymentDao
import com.example.app_bancario_teste.data.local.entity.PaymentEntity
import com.example.app_bancario_teste.data.remote.service.PaymentsService
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.domain.repository.PaymentRepository
import retrofit2.HttpException
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val paymentsService: PaymentsService,
    private val paymentDao: PaymentDao,
) : PaymentRepository {
    override suspend fun getPayments(): List<AccountPayment> {
        try {
            val response = paymentsService.getPayments()
            if (!response.isSuccessful) {
                throw RepositoryException("Erro ao busca dados de pagamentos")
            }
            val payments = response.body()
            if (payments.isNullOrEmpty()) {
                return emptyList()
            }

            val paymentEntity = payments.map { PaymentEntity(it) }
            paymentDao.savePayment(*paymentEntity.toTypedArray())

            return payments

        } catch (httpException: HttpException) {
            Log.e("repositoryError", "getPayments: ${httpException.message}")
            throw RepositoryException(message = "Erro ao buscar dados de contas")
        }

    }
}