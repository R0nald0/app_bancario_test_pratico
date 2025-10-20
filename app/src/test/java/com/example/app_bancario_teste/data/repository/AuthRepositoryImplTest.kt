package com.example.app_bancario_teste.data.repository

import com.example.app_bancario_teste.core.constants.AppConstants
import com.example.app_bancario_teste.data.local.service.PreferenceService
import com.example.app_bancario_teste.data.remote.service.AuthService
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.domain.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest {
    @Mock
    lateinit var authService: AuthService

    @Mock
    lateinit var prefereceService: PreferenceService

    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authRepository =
            AuthRepositoryImpl(authService = authService, preferences = prefereceService)
    }

    @Test
    fun `getUser_should return a customer data api`() = runTest {
        val mockCustomer = listOf(
            Customer(
                accountNumber = "12",
                id = "01",
                checkingAccountBalance = 6,
                customerName = "Tech",
                branchNumber = "2222"
            )
        )
        Mockito.`when`(authService.getUser()).thenReturn(
            Response.success(mockCustomer)
        )
        Mockito.`when`(
            prefereceService.saveOnPreferenceStore(
                key = AppConstants.CUSTOMER_PREFERENCE_KEY,
                value =  Gson().toJson(mockCustomer.first())
            )
        ).thenReturn(
            Unit
        )

        val result = authRepository.getUser()

        assertThat(result.customerName).isNotEmpty()
        assertThat(result.customerName).isEqualTo("Tech")
        Mockito.verify(authService).getUser()

    }
    @Test
    fun `getUserFromPreferences should return a customer data preference`() = runTest {
     val mockCustomer  = Customer(
            accountNumber = "12",
            id = "01",
            checkingAccountBalance = 6,
            customerName = "Tech",
            branchNumber = "2222"
        )
        Mockito.`when`(prefereceService.readFromPreference(AppConstants.CUSTOMER_PREFERENCE_KEY)).thenReturn(
            flowOf(Gson().toJson(mockCustomer))
        )

        val  result = authRepository.getUserFromPreferences().first()

        assertThat(result.customerName).isEqualTo("Tech")
        Mockito.verify(prefereceService).readFromPreference(AppConstants.CUSTOMER_PREFERENCE_KEY)
    }

    @After
    fun tearDown() { }

}