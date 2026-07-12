package com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.PwdTextField
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.component.SettingsMainHeader
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel.UpdatePasswordAction
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel.UpdatePasswordState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel.UpdatePasswordViewModel
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel.UpdatePwdForm
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.button_update_password
import medicalplatformmobile.shared.generated.resources.input_confirm_password_header
import medicalplatformmobile.shared.generated.resources.input_confirm_password_placeloder
import medicalplatformmobile.shared.generated.resources.input_current_password_header
import medicalplatformmobile.shared.generated.resources.input_current_password_placeloder
import medicalplatformmobile.shared.generated.resources.input_new_password_header
import medicalplatformmobile.shared.generated.resources.input_new_password_placeloder
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UpdatePasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdatePasswordViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    UpdatePasswordScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                is UpdatePasswordAction.OnGoBackClicked -> {
                    onGoBackClicked()
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun UpdatePasswordScreenRoot(
    modifier: Modifier = Modifier,
    state: UpdatePasswordState,
    onAction: (UpdatePasswordAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SettingsMainHeader(
            showGoBackButton = true,
            showUserData = false,
            onGoBackButtonClicked = {
                onAction(UpdatePasswordAction.OnGoBackClicked)
            }
        )
        ChangePwdForm(
            state = state,
            onAction = onAction
        )
    }
}

@Composable
fun ChangePwdForm(
    state: UpdatePasswordState,
    onAction: (UpdatePasswordAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        PwdTextField(
            value = state.form.currentPassword,
            header = stringResource(UiRes.string.input_current_password_header),
            placeholder = stringResource(UiRes.string.input_current_password_placeloder),
            onValueChange = { onAction(UpdatePasswordAction.OnCurrentPasswordChanged(it)) },
            passwordVisible = state.currentPwdVisible,
            onPasswordVisibleChange = { onAction(UpdatePasswordAction.ChangeCurrentPwdVisibility) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PwdTextField(
            value = state.form.password,
            header = stringResource(UiRes.string.input_new_password_header),
            placeholder = stringResource(UiRes.string.input_new_password_placeloder),
            onValueChange = { onAction(UpdatePasswordAction.OnNewPasswordChanged(it)) },
            passwordVisible = state.pwdVisible,
            onPasswordVisibleChange = { onAction(UpdatePasswordAction.ChangePwdVisibility) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PwdTextField(
            value = state.form.passwordConfirm,
            header = stringResource(UiRes.string.input_confirm_password_header),
            placeholder = stringResource(UiRes.string.input_confirm_password_placeloder),
            onValueChange = { onAction(UpdatePasswordAction.OnNewPasswordConfirmChanged(it)) },
            passwordVisible = state.pwdConfirmVisible,
            onPasswordVisibleChange = { onAction(UpdatePasswordAction.ChangePwdConfirmVisibility) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onAction(UpdatePasswordAction.OnSubmit) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !state.isLoading && state.isValid,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(UiRes.string.button_update_password),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun ChangePwdFormPreview() {
    ChangePwdForm(
        state = UpdatePasswordState(
            isLoading = false,
            isValid = true,
            form = UpdatePwdForm(
                currentPassword = "oldPassword123",
                password = "newPassword456",
                passwordConfirm = "newPassword456"
            )
        ),
        onAction = {}
    )
}