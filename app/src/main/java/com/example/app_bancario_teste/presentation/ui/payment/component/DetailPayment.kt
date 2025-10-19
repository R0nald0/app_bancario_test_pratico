package com.example.app_bancario_teste.presentation.ui.payment.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_bancario_teste.domain.model.Customer
import com.example.app_bancario_teste.ui.theme.AppBancarioTesteTheme

@Composable
fun DetailPayments(modifier: Modifier = Modifier, customer: Customer) {
    val (accountNumber,branchNumber,checkingAccountBalance,customerName,_) = customer

    Text(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 20.dp),
        text ="Detalhes do Pagamentos", style = MaterialTheme.typography.titleLarge)
    Column (
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    ){
        Text(customerName, style = MaterialTheme.typography.labelMedium)
        Text("Agência: $branchNumber | Conta :$accountNumber", style = MaterialTheme.typography.labelSmall)
    }
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text="Saldo: R$ $checkingAccountBalance", style = MaterialTheme.typography.bodyMedium)

}

@Preview
@Composable
private fun DetailPaymentsPrev() {
    AppBancarioTesteTheme {
        DetailPayments(customer = Customer(id = "1", customerName = "Teste", branchNumber = "02", checkingAccountBalance = 15000, accountNumber = "999-33"))
    }
}