package com.example.app_bancario_teste

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_bancario_teste.presentation.ui.login.LoginScreen
import com.example.app_bancario_teste.presentation.ui.payment.PaymentScreen
import com.example.app_bancario_teste.ui.theme.AppBancarioTesteTheme

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
                            onLogin = {
                                navController.navigate("/payments")
                            }
                        )
                    }

                    composable("/payments") {
                        PaymentScreen(
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

