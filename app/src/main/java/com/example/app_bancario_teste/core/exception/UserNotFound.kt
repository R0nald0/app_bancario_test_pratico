package com.example.app_bancario_teste.core.exception

data class UserNotFound(override val cause: Throwable? = null, override val message: String? = null) :
    Throwable(message,cause) {

}
