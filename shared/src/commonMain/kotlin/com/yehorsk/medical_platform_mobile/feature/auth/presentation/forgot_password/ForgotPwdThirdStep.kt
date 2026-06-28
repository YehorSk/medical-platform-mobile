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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.ui.components.PwdTextField
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.confirm_password
import medicalplatformmobile.shared.generated.resources.password
import medicalplatformmobile.shared.generated.resources.reset_password
import org.jetbrains.compose.resources.stringResource

@Composable
fun ForgotPwdThirdStep(
    pwd: String,
    pwdConfirm: String,
    isEntryValid: Boolean,
    isVisible: Boolean,
    onPwdChanged: (String) -> Unit,
    onPwdConfirmChanged: (String) -> Unit,
    onPwdVisibilityChanged: () -> Unit,
    onButtonClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PwdTextField(
            value = pwd,
            header = stringResource(UiRes.string.password),
            onValueChange = { onPwdChanged(it) },
            passwordVisible = isVisible,
            onPasswordVisibleChange = { onPwdVisibilityChanged() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        PwdTextField(
            value = pwdConfirm,
            header = stringResource(UiRes.string.confirm_password),
            onValueChange = { onPwdConfirmChanged(it) },
            passwordVisible = isVisible,
            onPasswordVisibleChange = { onPwdVisibilityChanged() }
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
                text = stringResource(UiRes.string.reset_password),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}