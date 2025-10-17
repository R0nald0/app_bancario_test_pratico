package com.example.app_bancario_teste.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_bancario_teste.domain.usecase.GetUser
import com.example.app_bancario_teste.domain.usecase.ValidateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UiLoginState(
    val success: Boolean = false,
    val loading: Boolean = false,
    val error: Boolean = false,
    val isEmailIValidate : String? = null,
    val isPasswordIValidate : String? = null,
    val message: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val validateUseCase: ValidateUseCase,
    private val getUser: GetUser,
) : ViewModel() {
    private val _loginUi = MutableStateFlow(UiLoginState())
    var loginUi: StateFlow<UiLoginState> = _loginUi.asStateFlow()

    fun validateEmail(email: String){
        val validate = validateUseCase.validate(email, "")
        if (validate.containsKey("email")){
          _loginUi.update {
              it.copy(isEmailIValidate =  validate.getValue("email"))
          }
          return
        }
        _loginUi.update {
            it.copy(isEmailIValidate = "")
        }
    }
    fun validatePassword(password: String){
        val validate = validateUseCase.validate("",password)
        if (validate.containsKey("password")){
            _loginUi.update {
                it.copy(isPasswordIValidate =  validate.getValue("password"))
            }
            return
        }
        _loginUi.update {
            it.copy(isPasswordIValidate = "")
        }
    }

    fun login(email: String, password: String) {
         _loginUi.update {
             it.copy(loading = true)
         }
        viewModelScope.launch {
            runCatching {
                getUser()
            }.fold(
                onSuccess = {costumer ->
                    Log.i("Info", "login: ${costumer.customerName}")
                    _loginUi.update {
                        it.copy(
                            success = true,
                            loading = false)
                    }
                },
                onFailure = {erro ->
                    Log.i("Info", "login: $erro")
                    _loginUi.update {

                        it.copy(
                            error = true,
                            success = false,
                            loading = false)
                    }
                }
            )

        }
    }

}