package com.example.app_bancario_teste.domain.repository

import com.example.app_bancario_teste.domain.model.AccountPayment

interface PaymentRepository {
    suspend fun getPayments() : List<AccountPayment>
}