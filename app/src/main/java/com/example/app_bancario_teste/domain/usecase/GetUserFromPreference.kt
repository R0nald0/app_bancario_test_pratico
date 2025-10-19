package com.example.app_bancario_teste.domain.usecase

import com.example.app_bancario_teste.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserFromPreference @Inject constructor(
  private val  authRepository: AuthRepository
) {

    suspend operator  fun invoke() = authRepository.getUserFromPreferences()
}