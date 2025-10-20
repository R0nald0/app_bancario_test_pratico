package com.example.app_bancario_teste.presentation.ui.login

import com.example.app_bancario_teste.domain.usecase.GetUser
import com.example.app_bancario_teste.domain.usecase.ValidateUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
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
        Mockito.`when`(validateUseCase.validate("email@",""))
            .thenReturn(t)
        loginViewModel.validateEmail("email")

        val result  = loginViewModel.loginUi.first().isEmailIValidate
        assertThat(result).isEmpty()
    }


    @After
    fun tearDown() {

    }

}