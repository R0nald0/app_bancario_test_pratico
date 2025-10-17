package com.example.app_bancario_teste.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_bancario_teste.R
import com.example.app_bancario_teste.presentation.components.AppBarCustom
import com.example.app_bancario_teste.presentation.components.AppTextField
import com.example.app_bancario_teste.presentation.components.LoadingOverlay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    onLogin: () -> Unit
) {
    val state by authViewModel.loginUi.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { AppBarCustom(title = "Login") }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            LoginContent(
                modifier = modifier,
                state = state,
                onLogin = onLogin,
                authViewModel = authViewModel
            )

            LoadingOverlay(isVisible = state.loading)
        }
    }
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    state: UiLoginState,
    onLogin: () -> Unit,
    authViewModel: AuthViewModel
) {
    val scrollState = rememberScrollState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state.success) {
        if (state.success) onLogin()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(scrollState)
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            painter = painterResource(R.drawable.bg_app),
            contentDescription = "image app",
            contentScale = ContentScale.Crop
        )

        AppTextField(
            label = "Email",
            value = email,
            errorText = state.isEmailIValidate,
            isError = state.isEmailIValidate != null,
            onChange = {
                email = it
                authViewModel.validateEmail(it)
            }
        )

        AppTextField(
            label = "Password",
            value = password,
            errorText = state.isPasswordIValidate,
            isError = state.isPasswordIValidate != null,
            isPassword = true,
            onChange = {
                password = it
                authViewModel.validatePassword(it)
            }
        )

        ElevatedButton(
            onClick = { authViewModel.login(email, password) },
            enabled = state.isEmailIValidate?.isEmpty() == true && state.isPasswordIValidate?.isEmpty() == true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color(0xff358073),
                disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
            )
        ) {
            Text(
                text = "Login",
                color = Color(0xffF7FAFC),
                style = TextStyle(
                    fontWeight = FontWeight.W700,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            )
        }
    }
}