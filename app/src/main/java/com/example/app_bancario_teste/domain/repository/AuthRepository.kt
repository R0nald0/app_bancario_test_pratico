package com.example.app_bancario_teste.domain.repository

import com.example.app_bancario_teste.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun  getUser(): Customer
    suspend fun getUserFromPreferences(): Flow<Customer>
}