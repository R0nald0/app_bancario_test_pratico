package com.example.app_bancario_teste.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.app_bancario_teste.presentation.ui.login.LoginScreen
import com.example.app_bancario_teste.ui.theme.AppBancarioTesteTheme

@Composable
fun LoadingOverlay(isVisible: Boolean) {
    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Gray)
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun LoginScreenPrev() {
    AppBancarioTesteTheme {
        LoginScreen(onLogin = {})
    }
}
