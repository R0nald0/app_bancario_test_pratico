package com.example.app_bancario_teste.di

import com.example.app_bancario_teste.data.repository.AuthRepository
import com.example.app_bancario_teste.domain.usecase.GetUser
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
    fun provideGetUser(authRepository: AuthRepository) = GetUser(authRepository = authRepository)
}