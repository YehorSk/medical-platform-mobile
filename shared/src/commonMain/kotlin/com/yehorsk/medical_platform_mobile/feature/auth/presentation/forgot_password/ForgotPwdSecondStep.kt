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
import medicalplatformmobile.shared.generated.resources.otp_input_placeholder
import medicalplatformmobile.shared.generated.resources.otp_verify_btn
import org.jetbrains.compose.resources.stringResource

@Composable
fun ForgotPwdSecondStep(
    code: String,
    isEntryValid: Boolean,
    onCodeChanged: (String) -> Unit,
    onButtonClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DefaultTextField(
            value = code,
            header = stringResource(UiRes.string.otp_input_placeholder),
            placeholder = stringResource(UiRes.string.otp_input_placeholder),
            onValueChange = { onCodeChanged(it) },
            keyboardType = KeyboardType.Number
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
                text = stringResource(UiRes.string.otp_verify_btn),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
