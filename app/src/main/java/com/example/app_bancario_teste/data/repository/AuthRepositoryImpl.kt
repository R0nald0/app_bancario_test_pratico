package com.example.app_bancario_teste.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import com.example.app_bancario_teste.core.constants.AppConstants
import com.example.app_bancario_teste.core.exception.RepositoryException
import com.example.app_bancario_teste.core.exception.UserNotFound
import com.example.app_bancario_teste.data.local.service.PreferenceService
import com.example.app_bancario_teste.data.remote.service.AuthService
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.domain.repository.AuthRepository
import com.google.gson.Gson
import com.google.gson.JsonIOException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val restClient: Retrofit,
    private val preferences: PreferenceService

) : AuthRepository {
    override suspend fun getUser(): Customer {
        try {
            val response = restClient.create(AuthService::class.java).getUser()
            if (!response.isSuccessful) throw UserNotFound()

            val customer = response.body()
            if (customer.isNullOrEmpty()) {
                throw UserNotFound()
            }
            saveUserOnPreferences(customer)
            return customer.first()
        } catch (httpException: HttpException) {
            Log.e("repository", "getUser: ${httpException.message} ", httpException)
            throw UserNotFound()
        } catch (repositoryException: RepositoryException) {
            throw RepositoryException(repositoryException.message)
        }
    }

    private suspend fun saveUserOnPreferences(customer: List<Customer>) {
        try {
            val customerJson = Gson().toJson(customer.first())
            preferences.saveOnPreferenceStore(
            value = customerJson,
            AppConstants.CUSTOMER_PREFERENCE_KEY
        )
        } catch (jsonIOException: JsonIOException) {
            Log.e("repository", "getUser: ${jsonIOException.message}", jsonIOException)
            throw RepositoryException(jsonIOException.message ?: "Erro ao converter dados")
        }catch (ioException: IOException) {
            Log.e("repository", "getUser: ${ioException.message}", ioException)
            throw RepositoryException(ioException.message ?: "Erro ao gravar dados")
        }
    }

    override suspend fun getUserFromPreferences() =
        preferences.readFromPreference(AppConstants.CUSTOMER_PREFERENCE_KEY)
            .map {customerJson ->
                val json = customerJson ?: throw  RepositoryException("Dados do customer ausentes")
                Gson().fromJson(json, Customer::class.java)
            }
            .catch { error->
            Log.e("repository", "getUserFromPreferences: ${error.message}", error)
            throw RepositoryException(error.message ?: "Erro ao recuperar dados do customer")

    }




}