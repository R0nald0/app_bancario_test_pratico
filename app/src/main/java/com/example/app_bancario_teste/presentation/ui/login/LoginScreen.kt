package com.example.app_bancario_teste.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_bancario_teste.R
import com.example.app_bancario_teste.presentation.components.AppBarCustom
import com.example.app_bancario_teste.presentation.components.AppTextField
import com.example.app_bancario_teste.ui.theme.AppBancarioTesteTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier,onLogin :() ->Unit) {
    Scaffold(
        topBar = {
            AppBarCustom(
                title = "Login"
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var isError by remember { mutableStateOf(true) }

            Image(
                modifier = Modifier
                    .height(239.dp)
                    .width(358.dp)
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = painterResource(R.drawable.bg_app),
                contentDescription = "image app",
                contentScale = ContentScale.Crop
            )

            AppTextField(
                modifier = modifier,
                errorText = "",
                label = "Email",
                value = email,
                isError = false,
                onChange = {value ->
                    email = value
                },
            )

            AppTextField(
                modifier = modifier,
                errorText = "Invalid password",
                label = "Password",
                value = password,
                isError = isError,
                isPassword = true,
                onChange = {value ->
                    password =value
                },
            )

            ElevatedButton(
                enabled = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .height(48.dp)
                    .width(358.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(0xff358073)
                ),
                onClick = onLogin
            ) {
                Text(
                    text = "Login", color = Color(0xffF7FAFC), style = TextStyle(
                        fontWeight = FontWeight.W700,
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                )
            }
        }
    }

}



@Preview
@Composable
private fun LoginScreenPrev() {
    AppBancarioTesteTheme {
        LoginScreen(
            onLogin = {}
        )
    }
}