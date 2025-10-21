package com.example.app_bancario_teste.presentation.ui.payment

import app.cash.turbine.test
import com.example.app_bancario_teste.core.exception.RepositoryException
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.domain.usecase.GetPayments
import com.example.app_bancario_teste.domain.usecase.GetUserFromPreference
import com.example.app_bancario_teste.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

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
class PaymentsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var getUserFromPreference: GetUserFromPreference

    @Mock
    lateinit var getPaymentFromUser: GetPayments

    lateinit var paymentsViewModel: PaymentsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        paymentsViewModel =
            PaymentsViewModel(
                getPaymentsUseCase = getPaymentFromUser,
                getUserFromPreference = getUserFromPreference
            )
    }


    @Test
    fun `getData should load de from ui`() = runTest {
        val fakeAccounts = listOf(
            AccountPayment(id = "1", paymentDate = "12/10/2020", electricityBill = "1233"),
            AccountPayment(id = "2", paymentDate = "12/10/2020", electricityBill = "1233"),
            AccountPayment(id = "3", paymentDate = "12/10/2020", electricityBill = "1233"),
        )
        val mockCustomer = Customer(
            accountNumber = "1",
            customerName = "Teste",
            checkingAccountBalance = 2,
            id = "2",
            branchNumber = "1331"
        )

        Mockito.`when`(getPaymentFromUser()).thenReturn(fakeAccounts)
        Mockito.`when`(getUserFromPreference()).thenReturn(flowOf(mockCustomer))

        paymentsViewModel.getData()

        paymentsViewModel.paymentUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            val stateSuccess = awaitItem()
            assertThat(stateSuccess.isLoading).isFalse()

            assertThat(stateSuccess.customer).isNotNull()
            assertThat(stateSuccess.customer?.customerName).isEqualTo("Teste")

            assertThat(stateSuccess.payments).isNotEmpty()
            assertThat(stateSuccess.payments.size).isEqualTo(3)

            assertThat(stateSuccess.message).isNull()

            cancelAndIgnoreRemainingEvents()
        }
        Mockito.verify(getPaymentFromUser, times(1)).invoke()
        Mockito.verify(getPaymentFromUser, times(1)).invoke()
    }


    @Test
    fun `getData should launch error when customer is null`() = runTest {
        val fakeAccounts = listOf(
            AccountPayment(id = "1", paymentDate = "12/10/2020", electricityBill = "1233"),
            AccountPayment(id = "2", paymentDate = "12/10/2020", electricityBill = "1233"),
            AccountPayment(id = "3", paymentDate = "12/10/2020", electricityBill = "1233"),
        )
        Mockito.`when`(getPaymentFromUser()).thenReturn(fakeAccounts)
        Mockito.`when`(getUserFromPreference()).thenAnswer {
            throw RepositoryException("Erro ao buscar dados do customer")
        }

        paymentsViewModel.getData()

        paymentsViewModel.paymentUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            awaitItem()
            val errorCustomerState = awaitItem()
            assertThat(errorCustomerState.isLoading).isFalse()

            assertThat(errorCustomerState.payments).isNotEmpty()
            assertThat(errorCustomerState.payments.size).isEqualTo(3)

            assertThat(errorCustomerState.customer).isNull()
            assertThat(errorCustomerState.message).isEqualTo("Erro ao buscar dados do cliente")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getData should launch error if not found list of payments and empty list`() = runTest {
        val mockCustomer = Customer(
            accountNumber = "1",
            customerName = "Teste",
            checkingAccountBalance = 2,
            id = "2",
            branchNumber = "1331"
        )

        Mockito.`when`(getUserFromPreference()).thenReturn(flowOf(mockCustomer))
        Mockito.`when`(getPaymentFromUser()).thenAnswer {
            throw RepositoryException("Erro ao buscar dados de pagamentos")
        }

        paymentsViewModel.getData()

        paymentsViewModel.paymentUiState.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            awaitItem()

            val errorCustomerState = awaitItem()

            assertThat(errorCustomerState.isLoading).isFalse()
            assertThat(errorCustomerState.payments).isEmpty()

            assertThat(errorCustomerState.customer).isEqualTo(mockCustomer)
            assertThat(errorCustomerState.message).isEqualTo("Erro ao buscar dados de pagamentos")
            cancelAndIgnoreRemainingEvents()
        }

        Mockito.verify(getPaymentFromUser, times(1)).invoke()
        Mockito.verify(getPaymentFromUser, times(1)).invoke()
    }


}