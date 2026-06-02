package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import com.yehorsk.medical_platform_mobile.core.ui.components.PwdTextField
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterAction
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterState
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.confirm_password
import medicalplatformmobile.shared.generated.resources.email_input
import medicalplatformmobile.shared.generated.resources.first_name
import medicalplatformmobile.shared.generated.resources.last_name
import medicalplatformmobile.shared.generated.resources.password
import medicalplatformmobile.shared.generated.resources.phone
import org.jetbrains.compose.resources.stringResource

@Composable
fun PatientRegisterForm(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            value = state.registerForm.firstName,
            header = stringResource(UiRes.string.first_name),
            placeholder = stringResource(UiRes.string.first_name),
            onValueChange = { onAction(RegisterAction.UpdateFirstName(it)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(
            value = state.registerForm.lastName,
            header = stringResource(UiRes.string.last_name),
            placeholder = stringResource(UiRes.string.last_name),
            onValueChange = { onAction(RegisterAction.UpdateLastName(it)) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(
            value = state.registerForm.email,
            header = stringResource(UiRes.string.email_input),
            placeholder = stringResource(UiRes.string.email_input),
            onValueChange = { onAction(RegisterAction.UpdateEmail(it)) },
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(
            value = state.registerForm.phone,
            header = stringResource(UiRes.string.phone),
            placeholder = stringResource(UiRes.string.phone),
            onValueChange = { onAction(RegisterAction.UpdatePhone(it)) },
            keyboardType = KeyboardType.Phone
        )
        Spacer(modifier = Modifier.height(8.dp))
        PwdTextField(
            value = state.registerForm.password,
            header = stringResource(UiRes.string.password),
            onValueChange = { onAction(RegisterAction.UpdatePwd(it)) },
            passwordVisible = state.passwordVisible,
            onPasswordVisibleChange = { onAction(RegisterAction.ChangePwdVisibility) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        PwdTextField(
            value = state.registerForm.passwordConfirm,
            header = stringResource(UiRes.string.confirm_password),
            onValueChange = { onAction(RegisterAction.UpdatePwdRepeat(it)) },
            passwordVisible = state.passwordVisible,
            onPasswordVisibleChange = { onAction(RegisterAction.ChangePwdVisibility) }
        )
    }
}