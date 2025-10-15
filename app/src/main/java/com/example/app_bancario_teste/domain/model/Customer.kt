package com.example.app_bancario_teste.domain.model

data class Customer(
    val accountNumber: String,
    val branchNumber: String,
    val checkingAccountBalance: Int,
    val customerName: String,
    val id: String
)