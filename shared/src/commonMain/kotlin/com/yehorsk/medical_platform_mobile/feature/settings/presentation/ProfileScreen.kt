package com.yehorsk.medical_platform_mobile.feature.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginAction
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsAction
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.component.SettingsMainHeader
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.address_input
import medicalplatformmobile.shared.generated.resources.email_input
import medicalplatformmobile.shared.generated.resources.email_input_placeholder
import medicalplatformmobile.shared.generated.resources.emergency_contact_name_input
import medicalplatformmobile.shared.generated.resources.emergency_contact_phone_input
import medicalplatformmobile.shared.generated.resources.first_name_input
import medicalplatformmobile.shared.generated.resources.last_name_input
import medicalplatformmobile.shared.generated.resources.phone_input
import medicalplatformmobile.shared.generated.resources.save_btn
import medicalplatformmobile.shared.generated.resources.sign_in
import medicalplatformmobile.shared.generated.resources.sign_up
import medicalplatformmobile.shared.generated.resources.title_input
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is SettingsAction.OnGoBackClicked -> {
                    onGoBackClicked()
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SettingsMainHeader(
            state = state,
            showGoBackButton = true,
            showUserData = false,
            onGoBackButtonClicked = {
                onAction(SettingsAction.OnGoBackClicked)
            }
        )
        ProfileForm(
            state = state,
            onAction = { onAction(it) }
        )
    }
}

@Composable
fun ProfileForm(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        DefaultTextField(
            value = state.form.firstName,
            header = stringResource(UiRes.string.first_name_input),
            placeholder = stringResource(UiRes.string.first_name_input),
            onValueChange = { onAction(SettingsAction.UpdateFirstName(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            value = state.form.lastName,
            header = stringResource(UiRes.string.last_name_input),
            placeholder = stringResource(UiRes.string.last_name_input),
            onValueChange = { onAction(SettingsAction.UpdateSecondName(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            value = state.form.title,
            header = stringResource(UiRes.string.title_input),
            placeholder = stringResource(UiRes.string.title_input),
            onValueChange = { onAction(SettingsAction.UpdateTitle(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            value = state.form.phone,
            header = stringResource(UiRes.string.phone_input),
            placeholder = stringResource(UiRes.string.phone_input),
            onValueChange = { onAction(SettingsAction.UpdatePhone(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            value = state.form.address,
            header = stringResource(UiRes.string.address_input),
            placeholder = stringResource(UiRes.string.address_input),
            onValueChange = { onAction(SettingsAction.UpdateAddress(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            value = state.form.emergencyContactName,
            header = stringResource(UiRes.string.emergency_contact_name_input),
            placeholder = stringResource(UiRes.string.emergency_contact_name_input),
            onValueChange = { onAction(SettingsAction.UpdateEmergencyContactName(it)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultTextField(
            value = state.form.emergencyContactPhone,
            header = stringResource(UiRes.string.emergency_contact_phone_input),
            placeholder = stringResource(UiRes.string.emergency_contact_phone_input),
            onValueChange = { onAction(SettingsAction.UpdateEmergencyContactPhone(it)) }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onAction(SettingsAction.OnSaveDataClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            enabled = !state.isLoading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = stringResource(UiRes.string.save_btn),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}