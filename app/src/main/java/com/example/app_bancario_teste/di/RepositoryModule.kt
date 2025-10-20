package com.example.app_bancario_teste.di

import com.example.app_bancario_teste.data.local.dao.PaymentDao
import com.example.app_bancario_teste.data.local.service.PreferenceService
import com.example.app_bancario_teste.data.remote.service.AuthService
import com.example.app_bancario_teste.data.remote.service.PaymentsService
import com.example.app_bancario_teste.data.repository.AuthRepositoryImpl
import com.example.app_bancario_teste.data.repository.PaymentsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService,preferenceService: PreferenceService) : AuthRepositoryImpl {
      return  AuthRepositoryImpl(authService = authService, preferences = preferenceService)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(paymentsService: PaymentsService,paymentDao: PaymentDao): PaymentsRepositoryImpl {
      return  PaymentsRepositoryImpl ( paymentsService = paymentsService , paymentDao = paymentDao)
    }
}
