package com.example.app_bancario_teste.presentation.ui.login

import com.example.app_bancario_teste.domain.model.Customer

sealed class ResultLogin()
class Success(val customer: Customer) : ResultLogin()
class InitialState : ResultLogin()
class Loading : ResultLogin()
class Error(val errorMessage: String) : ResultLogin()