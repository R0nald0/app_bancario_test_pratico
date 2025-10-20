package com.example.app_bancario_teste.domain.usecase

import com.example.app_bancario_teste.domain.repository.AuthRepository
import javax.inject.Inject

class GetUser @Inject constructor(
    private val authRepositoryImpl: AuthRepository
) {

    suspend operator fun invoke() = authRepositoryImpl.getUser()
}