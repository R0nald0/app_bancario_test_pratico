package com.example.app_bancario_teste.presentation.components

import android.provider.CalendarContract
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.app_bancario_teste.ui.theme.AppBancarioTesteTheme

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    errorText: String? = null,
    label: String,
    value: String,
    isError: Boolean,
    isPassword : Boolean = false,
    onChange: (String) -> Unit
) {

    TextField(
        modifier = modifier
            .width(358.dp),
        value = value,
        singleLine = true,
        maxLines = 1,
        onValueChange = onChange,
        visualTransformation = if (isPassword) PasswordVisualTransformation()  else VisualTransformation.None ,
        placeholder = { Text(label) },
        shape = RoundedCornerShape(15.dp),
        colors = TextFieldDefaults.colors(
            focusedLabelColor = Color(0xff5C738A),
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            errorLabelColor = Color.Red,
        ),
        supportingText = {
            if (isError && errorText != null) {
                Text(errorText)
            }
        },
        isError = isError
    )
}

@Preview
@Composable
private fun AppTextFieldPrev() {
    AppBancarioTesteTheme {
        AppTextField(
            onChange = {},
            errorText = "Error ",
            label = "Email",
            value = "",
            isError = false,
        )
    }
}