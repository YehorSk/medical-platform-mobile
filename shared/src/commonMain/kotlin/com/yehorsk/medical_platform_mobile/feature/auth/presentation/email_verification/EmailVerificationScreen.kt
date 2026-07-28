package com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.viewmodel.EmailVerificationAction
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.viewmodel.EmailVerificationState
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.viewmodel.EmailVerificationViewModel
import com.yehorsk.theme.AppTheme
import com.yehorsk.theme.extended
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.app_description
import medicalplatformmobile.shared.generated.resources.app_name
import medicalplatformmobile.shared.generated.resources.close
import medicalplatformmobile.shared.generated.resources.email_verified_failed
import medicalplatformmobile.shared.generated.resources.email_verified_successfully
import medicalplatformmobile.shared.generated.resources.email_verified_successfully_desc
import medicalplatformmobile.shared.generated.resources.sign_in
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import medicalplatformmobile.shared.generated.resources.verifying_account
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationScreen(
    modifier: Modifier = Modifier,
    viewModel: EmailVerificationViewModel = koinViewModel(),
    goToLoginPage: () -> Unit
){

    val state by viewModel.state.collectAsStateWithLifecycle()

    EmailVerificationScreenRoot(
        modifier = modifier,
        state = state,
        token = viewModel.token ?: "Empty",
        onAction = { action ->
            when(action){
                EmailVerificationAction.OnCloseClick -> { goToLoginPage() }
                EmailVerificationAction.OnLoginClick -> { goToLoginPage() }
            }
        }
    )

}

@Composable
fun EmailVerificationScreenRoot(
    modifier: Modifier = Modifier,
    state: EmailVerificationState,
    token: String = "Empty",
    onAction: (EmailVerificationAction) -> Unit
){
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
    ) { paddingValues ->
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
                        text = token,
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
                AnimatedVisibility(state.isVerifying){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(64.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(UiRes.string.verifying_account),
                            color = MaterialTheme.colorScheme.extended.textSecondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                AnimatedVisibility(state.isVerified){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(UiRes.string.email_verified_successfully),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.extended.textPrimary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(UiRes.string.email_verified_successfully_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.extended.textSecondary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        DefaultButton(
                            onClick = { onAction(EmailVerificationAction.OnLoginClick) },
                            text = stringResource(UiRes.string.sign_in)
                        )
                    }
                }
                AnimatedVisibility(!state.isVerified && !state.isVerifying){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 200.dp)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            Alignment.CenterVertically
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(UiRes.string.email_verified_failed),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.extended.textPrimary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(UiRes.string.email_verified_failed),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.extended.textSecondary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        DefaultButton(
                            onClick = { onAction(EmailVerificationAction.OnCloseClick) },
                            text = stringResource(UiRes.string.close)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EmailVerificationScreenVerifyingPreview(){
    AppTheme {
        EmailVerificationScreenRoot(
            state = EmailVerificationState(
                isVerified = false,
                isVerifying = true
            ),
            onAction = {  },
        )
    }
}

@Preview
@Composable
fun EmailVerificationScreenVerifiedPreview(){
    AppTheme {
        EmailVerificationScreenRoot(
            state = EmailVerificationState(
                isVerified = true,
                isVerifying = false
            ),
            onAction = {  },
        )
    }
}

@Preview
@Composable
fun EmailVerificationScreenFailPreview(){
    AppTheme {
        EmailVerificationScreenRoot(
            state = EmailVerificationState(
                isVerified = false,
                isVerifying = false
            ),
            onAction = {  },
        )
    }
}