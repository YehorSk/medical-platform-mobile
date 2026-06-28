package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.components.ForgotPwdHeader
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.components.StepIndicator
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordAction
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordForm
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.ForgotPasswordState
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.PasswordResetStep
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.forgot_password
import medicalplatformmobile.shared.generated.resources.forgot_password_instructions
import medicalplatformmobile.shared.generated.resources.mail_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordScreenViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ForgotPasswordScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action -> viewModel.onAction(action)}
    )

}

@Composable
fun ForgotPasswordScreenRoot(
    modifier: Modifier= Modifier,
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit
) {
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
            ForgotPwdHeader(
                icon = painterResource(UiRes.drawable.mail_24px),
                title = stringResource(UiRes.string.forgot_password),
                description = stringResource(UiRes.string.forgot_password_instructions),
                content = {
                    StepIndicator(
                        currentStep = state.currentStep
                    )
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedContent(
                    targetState = state.currentStep,
                    transitionSpec = {
                        if (targetState.ordinal > initialState.ordinal) {
                            fadeIn(tween(300)) + slideInHorizontally { it } togetherWith
                                    fadeOut(tween(300)) + slideOutHorizontally { -it }
                        } else {
                            fadeIn(tween(300)) + slideInHorizontally { -it } togetherWith
                                    fadeOut(tween(300)) + slideOutHorizontally { it }
                        }
                    },
                    label = "stepTransition"
                ) { step ->
                    when (step) {
                        PasswordResetStep.Email -> ForgotPwdFirstStep(
                            email = state.form.email,
                            isEntryValid = state.isEntryValid,
                            onEmailChanged = { onAction(ForgotPasswordAction.UpdateEmail(it)) },
                            onButtonClicked = { onAction(ForgotPasswordAction.OnSendResetTokenClicked) }
                        )
                        PasswordResetStep.Code -> ForgotPwdSecondStep(
                            code = state.form.otpCode,
                            isEntryValid = state.isEntryValid,
                            onCodeChanged = { onAction(ForgotPasswordAction.UpdateCode(it)) },
                            onButtonClicked = { onAction(ForgotPasswordAction.OnSendCodeClicked) }
                        )
                        PasswordResetStep.Password -> ForgotPwdThirdStep(
                            pwd = state.form.newPassword,
                            pwdConfirm = state.form.newPasswordConfirm,
                            isEntryValid = state.isEntryValid,
                            isVisible = state.isPwdVisible,
                            onPwdChanged = { onAction(ForgotPasswordAction.UpdatePassword(it)) },
                            onPwdConfirmChanged = { onAction(ForgotPasswordAction.UpdatePasswordConfirm(it)) },
                            onPwdVisibilityChanged = { onAction(ForgotPasswordAction.OnChangePwdVisibilityClicked) },
                            onButtonClicked = { onAction(ForgotPasswordAction.OnSendNewPwdClicked) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordScreenRootPreview(){
    AppTheme { 
        ForgotPasswordScreenRoot(
            state = ForgotPasswordState(
                isLoading = false,
                isEntryValid = true,
                form = ForgotPasswordForm(email = "test@gmail.com")
            ),
            onAction = {}
        )
    }
}