package com.yehorsk.medical_platform_mobile.feature.auth.presentation.login

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.component.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.component.PwdTextField
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginAction
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginState
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.app_description
import medicalplatformmobile.shared.generated.resources.app_name
import medicalplatformmobile.shared.generated.resources.email_input
import medicalplatformmobile.shared.generated.resources.email_input_placeholder
import medicalplatformmobile.shared.generated.resources.forgot_password
import medicalplatformmobile.shared.generated.resources.password_input
import medicalplatformmobile.shared.generated.resources.sign_in
import medicalplatformmobile.shared.generated.resources.sign_up
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel= koinViewModel(),
    onSignUpClicked: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LoginScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action) {
                is LoginAction.OnSignUpClicked -> onSignUpClicked()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    state: LoginState,
    onAction: (LoginAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2B5CE6))
                .padding(vertical = 48.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(Color(0xFF4A72F0), CircleShape),
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
                    text = stringResource(UiRes.string.app_name),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(UiRes.string.app_description),
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
            Spacer(modifier = Modifier.height(24.dp))
            DefaultTextField(
                value = state.loginForm.email,
                header = stringResource(UiRes.string.email_input),
                placeholder = stringResource(UiRes.string.email_input_placeholder),
                onValueChange = { onAction(LoginAction.UpdateEmail(it)) },
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            PwdTextField(
                value = state.loginForm.password,
                header = stringResource(UiRes.string.password_input),
                onValueChange = { onAction(LoginAction.UpdatePwd(it)) },
                passwordVisible = state.passwordVisible,
                onPasswordVisibleChange = { onAction(LoginAction.ChangePwdVisibility) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                TextButton(onClick = {}) {
                    Text(stringResource(UiRes.string.forgot_password), color = Color(0xFF2B5CE6))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onAction(LoginAction.OnSignInClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2B5CE6))
            ) {
                Text(
                    text = stringResource(UiRes.string.sign_in),
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
                    onClick = { onAction(LoginAction.OnSignUpClicked) },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(stringResource(UiRes.string.sign_up), color = Color(0xFF2B5CE6), fontWeight = FontWeight.Bold)
                }
            }
        }

    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    MaterialTheme {
        LoginScreenRoot(
            state = LoginState(
                loginForm = LoginForm(
                    email = "test@gmail.com",
                    password = "123456"
                )
            ),
            onAction = {}
        )
    }
}