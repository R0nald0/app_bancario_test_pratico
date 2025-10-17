package com.example.app_bancario_teste.di

import com.example.app_bancario_teste.data.repository.AuthRepository
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
   fun providerAuthRepository(restClient : Retrofit)= AuthRepository(restClient = restClient)

}