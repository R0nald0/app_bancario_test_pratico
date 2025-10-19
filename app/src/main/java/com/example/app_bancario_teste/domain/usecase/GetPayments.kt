package com.example.app_bancario_teste.domain.usecase

import com.example.app_bancario_teste.domain.repository.PaymentRepository
import javax.inject.Inject

class GetPayments @Inject constructor(
    private val paymentRepository: PaymentRepository
) {
    suspend operator fun invoke() = paymentRepository.getPayments()
}