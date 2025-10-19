package com.example.app_bancario_teste.di

import com.example.app_bancario_teste.data.local.service.PreferenceService
import com.example.app_bancario_teste.data.local.service.impl.PreferencesServiceImpl
import com.example.app_bancario_teste.data.repository.PaymentsRepositoryImpl
import com.example.app_bancario_teste.domain.repository.PaymentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface IntefaceModule {
    @Binds
    fun bindPaymentRepository(paymentsRepositoryImpl: PaymentsRepositoryImpl): PaymentRepository

    @Binds
    fun  bindPreferencesService(preferencesServiceImpl: PreferencesServiceImpl): PreferenceService
 }