package com.example.app_bancario_teste.core.exception

data class RepositoryException(override val message: String) : Throwable(message = message)
