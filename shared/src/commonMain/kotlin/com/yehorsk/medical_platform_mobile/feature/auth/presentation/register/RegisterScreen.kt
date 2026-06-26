package com.yehorsk.medical_platform_mobile.feature.auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.component.RoleToggle
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.component.DoctorRegisterForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.component.PatientRegisterForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterAction
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.register.viewmodel.RegisterState
import com.yehorsk.medical_platform_mobile.util.getRole
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.create_account
import medicalplatformmobile.shared.generated.resources.create_doctor_account
import medicalplatformmobile.shared.generated.resources.create_patient_account
import medicalplatformmobile.shared.generated.resources.join_medconnect_today
import medicalplatformmobile.shared.generated.resources.sign_in
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterScreenViewModel= koinViewModel(),
    onSignInClicked: () -> Unit,
    onSignUpClicked: (UserRole) -> Unit,
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is RegisterAction.OnSignInClicked -> onSignInClicked()
//                is RegisterAction.OnRegisterClicked -> {
//                    if(state.registerForm.role.equals(UserRole.PATIENT.name, ignoreCase = true)){
//                        onSignUpClicked(UserRole.PATIENT)
//                    }else{
//                        onSignUpClicked(UserRole.DOCTOR)
//                    }
//                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
){
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
    ) { paddingValues ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(UiRes.drawable.stethoscope_24px),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(UiRes.string.create_account),
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(UiRes.string.join_medconnect_today),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                RoleToggle(
                    selectedRole = getRole(state.registerForm.role),
                    onRoleSelected = { onAction(RegisterAction.UpdateRole(it.name)) }
                )

                AnimatedVisibility(
                    visible = getRole(state.registerForm.role) == UserRole.PATIENT,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ){
                    PatientRegisterForm(
                        state = state,
                        onAction = { onAction(it) }
                    )
                }
                AnimatedVisibility(
                    visible = getRole(state.registerForm.role) == UserRole.DOCTOR,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    DoctorRegisterForm(
                        state = state,
                        onAction = { onAction(it) }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onAction(RegisterAction.OnRegisterClicked) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    enabled = state.isEntryValid,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(
                            if (getRole(state.registerForm.role) == UserRole.PATIENT)
                                UiRes.string.create_patient_account
                            else
                                UiRes.string.create_doctor_account
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onAction(RegisterAction.OnSignInClicked) },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(stringResource(UiRes.string.sign_in), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    AppTheme {
        RegisterScreenRoot(
            state = RegisterState(),
            onAction = {}
        )
    }
}