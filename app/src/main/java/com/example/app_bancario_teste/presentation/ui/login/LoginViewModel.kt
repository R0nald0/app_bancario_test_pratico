package com.example.app_bancario_teste.presentation.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


data class UiLoginState(
    val isEmailIValidate: String? = null,
    val isPasswordIValidate: String? = null,
    val message: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateUseCase: ValidateUseCase,
    private val getUser: GetUser,
) : ViewModel() {
    private val _loginUi = MutableStateFlow(UiLoginState())
    var loginUi: StateFlow<UiLoginState> = _loginUi.asStateFlow()
    private val _resultLoginState = MutableSharedFlow<ResultLogin>()
    var resultLoginState = _resultLoginState.asSharedFlow()


    fun validateEmail(email: String) {
        val validate = validateUseCase.validate(email, "")
        if (validate.containsKey("email")) {
            _loginUi.update {
                it.copy(isEmailIValidate = validate.getValue("email"))
            }
            return
        }
        _loginUi.update {
            it.copy(isEmailIValidate = "")
        }
    }

    fun validatePassword(password: String) {
        val validate = validateUseCase.validate("", password)
        if (validate.containsKey("password")) {
            _loginUi.update {
                it.copy(isPasswordIValidate = validate.getValue("password"))
            }
            return
        }
        _loginUi.update {
            it.copy(isPasswordIValidate = "")
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _resultLoginState.emit(Loading())
            runCatching {
                getUser()
            }.fold(
                onSuccess = { costumer ->
                    _loginUi.update {
                        it.copy(isPasswordIValidate = null, isEmailIValidate = null, message = null)
                    }
                    _resultLoginState.emit(Success(customer = costumer))
                },
                onFailure = { error ->
                    _resultLoginState.emit(Error(errorMessage = "Erro ao buscar dados do customer"))
                }
            )

        }
    }

}