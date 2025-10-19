package com.example.app_bancario_teste.presentation.ui.payment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.domain.usecase.GetPayments
import com.example.app_bancario_teste.domain.usecase.GetUserFromPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
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
): ViewModel() {
    private val  _paymentUiState = MutableStateFlow(PaymentUiState())
    val paymentUiState : StateFlow<PaymentUiState> =  _paymentUiState.asStateFlow()
    init {
        getPayments()
        getUser()

    }
    fun getUser(){
        viewModelScope.launch {
            //_paymentUiState.update { it.copy(isLoading = true, message = null) }
            getUserFromPreference()
                .catch {error ->
                    _paymentUiState.update {
                        it.copy( customer =  null , message = error.message )
                    }
                }
                .collect {customer ->
                    _paymentUiState.update {
                        it.copy(customer = customer)
                    }
               }
        }
    }
    fun getPayments(){
        _paymentUiState.update { it.copy(isLoading = true, message = null) }
        viewModelScope.launch {
            runCatching {
                delay(2000L)
                getPaymentsUseCase()
            }.fold(
                onSuccess = {payments ->
                    _paymentUiState.update {
                        it.copy(payments = payments, isLoading = false, message = null)
                    }
                },
                onFailure = {error->
                    _paymentUiState.update {
                        it.copy(payments = emptyList(), isLoading = false, message = error.message )
                    }
                }
            )
        }
    }
}