package com.example.app_bancario_teste.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_bancario_teste.presentation.theme.AppBancarioTesteTheme
import com.example.app_bancario_teste.presentation.ui.login.LoginScreen
import com.example.app_bancario_teste.presentation.ui.login.LoginViewModel
import com.example.app_bancario_teste.presentation.ui.payment.PaymentScreen
import com.example.app_bancario_teste.presentation.ui.payment.PaymentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppBancarioTesteTheme {
                NavHost(
                    navController = navController,
                    startDestination = "/login"
                ) {
                    composable("/login") {
                        LoginScreen(
                            loginViewModel = hiltViewModel<LoginViewModel>(),
                            onSuccessLogin = { customer ->
                                navController.navigate("/payments",)
                            }
                        )
                    }

                    composable("/payments") {
                        PaymentScreen(
                            paymentsViewModel = hiltViewModel<PaymentsViewModel>(),
                            onBackTap = {
                                navController.popBackStack()
                            }
                        )
                    }
                }

            }
        }
    }
}