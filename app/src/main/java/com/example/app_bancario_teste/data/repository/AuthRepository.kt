package com.example.app_bancario_teste.data.repository

import com.example.app_bancario_teste.core.exception.UserNotFound
import com.example.app_bancario_teste.data.remote.service.AuthService
import com.example.app_bancario_teste.domain.model.Customer
import retrofit2.Retrofit
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val  restClient : Retrofit
) {
    suspend fun  getUser(): Customer{
     try {
         val response  = restClient.create(AuthService::class.java).getUser()

         if (!response.isSuccessful || response.body() == null) throw UserNotFound()

        return response.body()!!.first()

     }catch (exception : Exception){
         throw UserNotFound()
     }
    }

}