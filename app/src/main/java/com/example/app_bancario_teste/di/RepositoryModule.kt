package com.example.app_bancario_teste.di

import com.example.app_bancario_teste.data.local.dao.AppDatabase
import com.example.app_bancario_teste.data.local.service.PreferenceService
import com.example.app_bancario_teste.data.repository.AuthRepositoryImpl
import com.example.app_bancario_teste.data.repository.PaymentsRepositoryImpl
import com.example.app_bancario_teste.domain.repository.AuthRepository
import com.example.app_bancario_teste.domain.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(restClient: Retrofit,preferenceService: PreferenceService) : AuthRepositoryImpl {
      return  AuthRepositoryImpl(restClient = restClient, preferences = preferenceService)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(restClient: Retrofit,appDatabase: AppDatabase): PaymentsRepositoryImpl {
      return  PaymentsRepositoryImpl(restClient = restClient, appDatabase = appDatabase)
    }
}
