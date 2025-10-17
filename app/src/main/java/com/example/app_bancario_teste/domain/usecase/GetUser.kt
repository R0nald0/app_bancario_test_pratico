package com.example.app_bancario_teste.domain.usecase

import com.example.app_bancario_teste.data.repository.AuthRepository
import javax.inject.Inject

class GetUser @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() = authRepository.getUser()
}