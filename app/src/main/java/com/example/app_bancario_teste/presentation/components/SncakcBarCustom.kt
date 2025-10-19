package com.example.app_bancario_teste.presentation.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SnackBarCustom(modifier: Modifier = Modifier,message: String) {
    Snackbar(
        modifier = modifier
    ){
        Text( text = message, color = Color.White)
    }

}