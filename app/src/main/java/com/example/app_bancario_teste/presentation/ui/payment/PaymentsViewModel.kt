package com.example.app_bancario_teste.presentation.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.domain.usecase.GetPayments
import com.example.app_bancario_teste.domain.usecase.GetUserFromPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PaymentUiState(
     val isLoading : Boolean = true,
     val customer: Customer? = null,
     val payments : List<AccountPayment> = emptyList(),
     val message : String ? = null
)

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val getPaymentsUseCase: GetPayments,
    private val getUserFromPreference: GetUserFromPreference
) : ViewModel() {

    private val _paymentUiState = MutableStateFlow(PaymentUiState())
    val paymentUiState: StateFlow<PaymentUiState> = _paymentUiState.asStateFlow()


    fun getData() {
        viewModelScope.launch {
            _paymentUiState.update { it.copy(isLoading = true, message = null) }

            val userResult = async { getUser() }
            val paymentsResult = async { getPayments() }

            val customer = userResult.await()
            val payments = paymentsResult.await()

            _paymentUiState.update {
                it.copy(
                    customer = customer,
                    payments = payments,
                    isLoading = false
                )
            }
        }
    }

    private suspend fun getUser(): Customer? {
      return  runCatching { getUserFromPreference().first() }
            .onFailure {
                _paymentUiState.update { it.copy(message = "Erro ao buscar dados do cliente") }
            }.getOrNull()
    }


    private suspend fun getPayments(): List<AccountPayment>{
      return  runCatching {
            getPaymentsUseCase()
        }
            .onFailure {
                _paymentUiState.update { it.copy(message = "Erro ao buscar dados de pagamentos") }
            }.getOrDefault(emptyList())
    }
}