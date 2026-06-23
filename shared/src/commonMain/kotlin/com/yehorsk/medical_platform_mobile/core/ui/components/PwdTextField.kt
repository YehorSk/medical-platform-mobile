package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.visibility_24px
import medicalplatformmobile.shared.generated.resources.visibility_off_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun PwdTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String= "••••••••",
    header: String? = null,
    passwordVisible: Boolean,
    onValueChange: (String) -> Unit,
    onPasswordVisibleChange: () -> Unit,
    error: String= ""
){
    header?.let {
        Text(it, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
    }
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(12.dp),
        placeholder = {
            Text(
                text = placeholder
            )
        },
        isError = error.isNotBlank(),
        supportingText = {
            if (error.isNotBlank()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF5F5F5),
            focusedContainerColor = Color(0xFFF5F5F5),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color(0xFF2B5CE6)
        ),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { onPasswordVisibleChange() }) {
                Icon(
                    painter = if (passwordVisible)
                        painterResource(UiRes.drawable.visibility_24px)
                    else
                        painterResource(UiRes.drawable.visibility_off_24px),
                    contentDescription = null
                )
            }
        },
        singleLine = true
    )
}

@Preview
@Composable
fun PwdTextFieldPreview(){
    MaterialTheme {
        PwdTextField(
            value = "",
            passwordVisible = false,
            onValueChange = {},
            onPasswordVisibleChange = {}
        )
    }
}