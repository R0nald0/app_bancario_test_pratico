package com.example.app_bancario_teste.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarCustom(
    modifier: Modifier = Modifier,
    title: String,
    showBackNavigation: Boolean = false,
    onBackButton: ()-> Unit = {}
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (showBackNavigation) {
                IconButton(
                    onClick = onBackButton
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back button",
                    )
                }

            }
        },
        title = {
            Text(
                style = TextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 18.sp,
                    lineHeight = 23.sp
                ),
                text = title,
                textAlign = TextAlign.Center
            )
        }
    )
}