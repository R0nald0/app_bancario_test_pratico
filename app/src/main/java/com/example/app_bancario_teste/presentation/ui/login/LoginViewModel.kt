package com.example.app_bancario_teste.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.domain.usecase.GetUser
import com.example.app_bancario_teste.domain.usecase.ValidateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ResultLogin()
class Success(val customer: Customer) : ResultLogin()
class InitialState : ResultLogin()
class Loading : ResultLogin()
class Error (val errorMessage: String) : ResultLogin()
data class UiLoginState(
    val resultLogin: ResultLogin = InitialState(),
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
    private val  _resultLoginState = MutableSharedFlow<ResultLogin>()
    var  resultLoginState  = _resultLoginState.asSharedFlow()


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
         /*_loginUi.update {
             it.copy(resultLogin = Loading())
         }*/
        viewModelScope.launch {
            _resultLoginState.emit(Loading())
            runCatching {
                getUser()
            }.fold(
                onSuccess = {costumer ->
                    _resultLoginState.emit(Success(customer = costumer))
                    /*Log.i("Info", "login: ${costumer.customerName}")
                    _loginUi.update {
                        it.copy(
                             resultLogin = Success(customer = costumer)
                             )
                    }*/
                },
                onFailure = {error ->
                    _resultLoginState.emit(Error(errorMessage = "Erro ao buscar dados do usuário"))

                    Log.i("Info", "login: $error")
                    /*_loginUi.update {
                        it.copy(resultLogin = Error(errorMessage = "Erro ao buscar dados do usuário"))
                    }*/
                }
            )

        }
    }

}