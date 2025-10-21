package com.example.app_bancario_teste.presentation.ui.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.app_bancario_teste.presentation.components.AppBarCustom
import com.example.app_bancario_teste.presentation.components.LoadingOverlay
import com.example.app_bancario_teste.presentation.components.SnackBarCustom
import com.example.app_bancario_teste.presentation.theme.AppBancarioTesteTheme
import com.example.app_bancario_teste.presentation.ui.payment.component.AccountItem
import com.example.app_bancario_teste.presentation.ui.payment.component.DetailPayments

@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    paymentsViewModel: PaymentsViewModel = hiltViewModel<PaymentsViewModel>(), onBackTap: () -> Unit
) {

    val state by paymentsViewModel.paymentUiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(Unit) {
        paymentsViewModel.getData()
    }


    if (state.isLoading) LoadingOverlay(true)
    else Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { data ->
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp, vertical = 16.dp),

            ) {
                item() {
                    state.customer?.let { customer ->
                        DetailPayments(modifier = modifier, customer)
                    }?:
                    NoDataFound(modifier = modifier, text = "Dados do cliente não foram encontrados")

                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                        text = "Contas pagas",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                if (state.payments.isEmpty()) {
                    item {
                        NoDataFound(modifier = modifier, text = "Não há registros de contas")
                    }
                }

               else items(
                    state.payments, key = { it.id }) { payment ->
                    AccountItem(payment = payment)
                }

        }
    }


    LaunchedEffect(state.message) {
        state.message?.let {
            snackBarHostState.showSnackbar(it)
        }
    }
}

@Composable
fun NoDataFound(modifier: Modifier = Modifier, text : String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier.height(40.dp))
        Text(text)
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

