package com.example.app_bancario_teste.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_bancario_teste.domain.model.AccountPayment


@Entity(tableName = "payment")
data class PaymentEntity (
    @PrimaryKey(autoGenerate = false)
    val id : Long,
    val paymentDate : String,
    val electricityBill : String,
    ){

    constructor(accountPayment: AccountPayment) :this(
        id = accountPayment.id.toLong(),
        paymentDate =accountPayment.paymentDate ,
        electricityBill = accountPayment.electricityBill
    )
}

fun PaymentEntity.toDomain() = AccountPayment(
    paymentDate = this.paymentDate,
    id = this.id.toString(),
    electricityBill = this.electricityBill
)