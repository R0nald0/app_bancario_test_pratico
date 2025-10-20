package com.example.app_bancario_teste.presentation.ui.payment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_bancario_teste.domain.model.AccountPayment
import com.example.app_bancario_teste.presentation.theme.AppBancarioTesteTheme
import com.example.app_bancario_teste.ui.theme.BackGroundColor

@Composable
fun AccountItem(modifier: Modifier = Modifier, payment: AccountPayment) {
    Row(
        modifier = modifier
            .background(color = BackGroundColor, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .height(72.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                "Conta de luz",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                payment.electricityBill,
                style = MaterialTheme.typography.labelSmall
            )
        }
        Text(payment.paymentDate, style = MaterialTheme.typography.labelSmall)
    }
}

@Preview
@Composable
private fun AccountItemPrev() {
    AppBancarioTesteTheme {
        AccountItem(
            payment = AccountPayment(
                electricityBill = "R$ 1.000,00",
                id = "1",
                paymentDate = "12/12/2020"
            )
        )

    }
}