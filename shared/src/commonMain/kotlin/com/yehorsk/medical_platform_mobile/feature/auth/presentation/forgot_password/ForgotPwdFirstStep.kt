package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.email_input
import medicalplatformmobile.shared.generated.resources.email_input_placeholder
import medicalplatformmobile.shared.generated.resources.send_reset_code
import org.jetbrains.compose.resources.stringResource

@Composable
fun ForgotPwdFirstStep(
    email: String,
    isEntryValid: Boolean,
    onEmailChanged: (String) -> Unit,
    onButtonClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DefaultTextField(
            value = email,
            header = stringResource(UiRes.string.email_input),
            placeholder = stringResource(UiRes.string.email_input_placeholder),
            onValueChange = { onEmailChanged(it) },
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onButtonClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = isEntryValid,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(UiRes.string.send_reset_code),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}