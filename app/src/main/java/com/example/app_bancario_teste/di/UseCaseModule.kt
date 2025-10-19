package com.example.app_bancario_teste.di

import com.example.app_bancario_teste.data.repository.AuthRepositoryImpl
import com.example.app_bancario_teste.domain.repository.PaymentRepository
import com.example.app_bancario_teste.domain.usecase.GetPayments
import com.example.app_bancario_teste.domain.usecase.GetUser
import com.example.app_bancario_teste.domain.usecase.GetUserFromPreference
import com.example.app_bancario_teste.domain.usecase.ValidateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideValidateUseCase() = ValidateUseCase()

    @Provides
    fun provideGetUser(authRepositoryImpl: AuthRepositoryImpl) =
        GetUser(authRepositoryImpl = authRepositoryImpl)

   @Provides
    fun provideGetUserFromPreference(authRepositoryImpl: AuthRepositoryImpl) =
        GetUserFromPreference(
            authRepository = authRepositoryImpl
        )

    @Provides
    fun providesGetPayments(paymentRepository: PaymentRepository) =
        GetPayments(paymentRepository = paymentRepository)

}