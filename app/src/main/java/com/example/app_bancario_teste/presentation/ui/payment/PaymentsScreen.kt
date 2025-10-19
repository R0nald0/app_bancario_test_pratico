package com.example.app_bancario_teste.presentation.ui.payment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_bancario_teste.presentation.components.AppBarCustom
import com.example.app_bancario_teste.presentation.components.LoadingOverlay
import com.example.app_bancario_teste.presentation.components.SnackBarCustom
import com.example.app_bancario_teste.presentation.ui.payment.component.AccountItem
import com.example.app_bancario_teste.presentation.ui.payment.component.DetailPayments
import com.example.app_bancario_teste.ui.theme.AppBancarioTesteTheme
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    paymentsViewModel: PaymentsViewModel = viewModel<PaymentsViewModel>(), onBackTap: () -> Unit
) {
    val state by paymentsViewModel.paymentUiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    if (state.isLoading) LoadingOverlay(true)
    else Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                SnackBarCustom(modifier = modifier, message = data.visuals.message)
            }
        },
        topBar = {
            AppBarCustom(
                title = "Pagamentos",
                showBackNavigation = true,
                onBackButton = onBackTap
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start,
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                item {
                    state.customer?.let { customer ->
                        DetailPayments(modifier = modifier, customer)
                    }

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                        text = "Contas pagas",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                items(state.payments, key = { it.id }) { payment ->
                    AccountItem(payment = payment)
                }
            }
        }
    }


    LaunchedEffect(state.message) {
        state.message?.let {
            snackbarHostState.showSnackbar(it)
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

