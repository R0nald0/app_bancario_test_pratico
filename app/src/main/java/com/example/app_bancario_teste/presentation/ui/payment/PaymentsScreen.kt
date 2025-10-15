package com.example.app_bancario_teste.presentation.ui.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.presentation.components.AppBarCustom
import com.example.app_bancario_teste.presentation.ui.payment.component.AccountItem
import com.example.app_bancario_teste.ui.theme.AppBancarioTesteTheme

@Composable
fun PaymentScreen(modifier: Modifier = Modifier, onBackTap: () -> Unit) {
    val payments = listOf(
        AccountPayment("R$ 120,00", "001", "10/10/2025"),
        AccountPayment("R$ 98,50", "002", "11/11/2025"),
        AccountPayment("R$ 140,75", "003", "12/12/2025"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
        AccountPayment("R$ 85,30", "004", "13/01/2026"),
    )

    Scaffold(
        topBar = {
            AppBarCustom(
                title = "Pagamentos",
                showBackNavigation = true,
                onBackButton = onBackTap
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {

            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                text ="Detalhes do Pagamentos", style = MaterialTheme.typography.titleLarge)
            Column (
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            ){
                Text("Cliente: Marina Silva", style = MaterialTheme.typography.labelMedium)
                Text("Agência: 1234 | Conta :5678-0", style = MaterialTheme.typography.labelSmall)
            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text="Saldo: R$ 1.5000,00", style = MaterialTheme.typography.bodyMedium)
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
               text = "Contas pagas", style = MaterialTheme.typography.titleLarge
            )

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(payments) { payment ->
                    AccountItem(payment = payment)
                }

            }


        }
    }
}

@Preview
@Composable
private fun PaymentsScreenPrev() {
    AppBancarioTesteTheme {
        PaymentScreen(
            onBackTap = {}
        )
    }
}