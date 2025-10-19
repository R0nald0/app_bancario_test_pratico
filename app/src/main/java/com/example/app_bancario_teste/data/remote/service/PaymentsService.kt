package com.example.app_bancario_teste.data.remote.service

import com.example.app_bancario_teste.domain.model.AccountPayment
import retrofit2.Response
import retrofit2.http.GET

interface PaymentsService {
    @GET("/treinamento/payments")
    suspend fun getPayments(): Response<List<AccountPayment>>
}