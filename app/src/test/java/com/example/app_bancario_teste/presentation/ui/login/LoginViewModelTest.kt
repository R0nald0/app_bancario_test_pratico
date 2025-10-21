package com.example.app_bancario_teste.presentation.ui.login

import app.cash.turbine.test
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.domain.usecase.GetUser
import com.example.app_bancario_teste.domain.usecase.ValidateUseCase
import com.example.app_bancario_teste.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    @Mock
    lateinit var getUser: GetUser

    @Mock
    lateinit var validateUseCase: ValidateUseCase

    lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loginViewModel= LoginViewModel(getUser = getUser, validateUseCase = validateUseCase)
    }

    @Test
    fun `should verify if email is invalid without @`() = runTest {
      val t = mapOf(pair = "email" to "e-mail inávlido,verfique o campo,email deve conter @")
        Mockito.`when`(validateUseCase.validate("email",""))
            .thenReturn(t)
         loginViewModel.validateEmail("email")

        val result  = loginViewModel.loginUi.first().isEmailIValidate
        assertThat(result).isNotEmpty()
        assertThat(result).isEqualTo("e-mail inávlido,verfique o campo,email deve conter @")
    }

    @Test
    fun `should verify if email valid with @`() = runTest {
        val t = mapOf(pair = "" to "")

        loginViewModel.validateEmail("email")

        val result  = loginViewModel.loginUi.first().isEmailIValidate
        assertThat(result).isEmpty()
    }

    @Test
    fun `login should emit Loading then Success when getUser returns customer`() = runTest {
        val fakeCustomer = Customer(
            branchNumber = "1200",
            accountNumber = "01",
            customerName = "Teste",
            id = "2",
            checkingAccountBalance = 32
        )

        Mockito.`when`(getUser()).thenReturn(fakeCustomer)

        loginViewModel.login("", "")


        loginViewModel.resultLoginState.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(Loading::class.java)

            val success = awaitItem()
            assertThat(success).isInstanceOf(Success::class.java)
            val result = success as Success
            assertThat(result.customer.customerName).isEqualTo("Teste")

            cancelAndIgnoreRemainingEvents()

        }

        Mockito.verify(getUser, times(1)).invoke()
    }

    @Test
    fun `login should emit Loading then Error when getUser throws exception`() = runTest {
        Mockito.`when`(getUser()).thenThrow(RuntimeException("Erro ao buscar dados do customer"))

        loginViewModel.login("", "")

        loginViewModel.resultLoginState.test {
            val loading = awaitItem()
            assertThat(loading).isInstanceOf(Loading::class.java)

            val error = awaitItem()
            assertThat(error).isInstanceOf(Error::class.java)
            val result = error as Error
            assertThat(result.errorMessage).isEqualTo("Erro ao buscar dados do customer")

            cancelAndIgnoreRemainingEvents()
        }

        Mockito.verify(getUser, times(1)).invoke()
    }




}