package com.example.app_bancario_teste.data.remote.service


import com.example.app_bancario_teste.domain.model.Customer
import retrofit2.Response
import retrofit2.http.GET

interface AuthService {
    
    @GET("treinamento/Login")
    suspend fun getUser() : Response<List<Customer>>
}