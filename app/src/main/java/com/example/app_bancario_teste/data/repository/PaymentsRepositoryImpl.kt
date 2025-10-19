package com.example.app_bancario_teste.data.repository

import android.util.Log
import com.example.app_bancario_teste.core.exception.RepositoryException
import com.example.app_bancario_teste.core.exception.UserNotFound
import com.example.app_bancario_teste.data.local.dao.AppDatabase
import com.example.app_bancario_teste.data.local.entity.PaymentEntity
import com.example.app_bancario_teste.data.remote.service.PaymentsService
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.domain.repository.PaymentRepository
import retrofit2.HttpException
import retrofit2.Retrofit
import javax.inject.Inject

class PaymentsRepositoryImpl @Inject constructor(
    private val restClient: Retrofit,
    private val appDatabase: AppDatabase,
) : PaymentRepository {
    override suspend fun getPayments(): List<AccountPayment> {
        try {
            val response = restClient.create(PaymentsService::class.java).getPayments()
            if (!response.isSuccessful) {
                throw RepositoryException("Erro ao busca dados de pagametos")
            }
            val payments = response.body()
            if (payments.isNullOrEmpty()) {
                return emptyList()
            }

            val paymentEntity = payments.map { PaymentEntity(it) }
            appDatabase.paymentDao().savePayment(*paymentEntity.toTypedArray())

            return payments

        } catch (httpException: HttpException) {
            Log.e("repositoryError", "getPayments: ${httpException.message}")
            throw RepositoryException(message = "Erro ao buscar dados de contas")
        }

    }
}