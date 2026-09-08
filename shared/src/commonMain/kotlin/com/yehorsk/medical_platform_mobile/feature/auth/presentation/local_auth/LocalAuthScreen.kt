package com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import com.yehorsk.medical_platform_mobile.core.util.ObserveAsEvents
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel.LocalAuthAction
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel.LocalAuthEvent
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel.LocalAuthScreenViewModel
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel.LocalAuthState
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel.LocalAuthType
import com.yehorsk.medical_platform_mobile.util.UiText
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.confirm_button
import medicalplatformmobile.shared.generated.resources.continue_button
import medicalplatformmobile.shared.generated.resources.error_pin_incorrect
import medicalplatformmobile.shared.generated.resources.pin_confirm_description
import medicalplatformmobile.shared.generated.resources.pin_confirm_input
import medicalplatformmobile.shared.generated.resources.pin_confirm_input_placeholder
import medicalplatformmobile.shared.generated.resources.pin_confirm_title
import medicalplatformmobile.shared.generated.resources.pin_create_description
import medicalplatformmobile.shared.generated.resources.pin_create_title
import medicalplatformmobile.shared.generated.resources.pin_enter_description
import medicalplatformmobile.shared.generated.resources.pin_enter_title
import medicalplatformmobile.shared.generated.resources.pin_input
import medicalplatformmobile.shared.generated.resources.pin_input_placeholder
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LocalAuthScreen(
    modifier: Modifier = Modifier,
    viewModel: LocalAuthScreenViewModel,
    onPinIsCorrect: (UserRole) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            LocalAuthEvent.Success -> onPinIsCorrect(state.userRole!!)
        }
    }

    LocalAuthScreenRoot(
        modifier = modifier,
        state = state,
        onAction = viewModel::onAction
    )
}


@Composable
fun LocalAuthScreenRoot(
    modifier: Modifier = Modifier,
    state: LocalAuthState,
    onAction: (LocalAuthAction) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
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
                            .background(
                                Color(0xFF4A72F0),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                UiRes.drawable.stethoscope_24px
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = when (state.localAuthType) {
                            LocalAuthType.CREATE_PIN ->
                                stringResource(UiRes.string.pin_create_title)

                            LocalAuthType.CONFIRM_PIN ->
                                stringResource(UiRes.string.pin_confirm_title)

                            LocalAuthType.ENTER_PIN ->
                                stringResource(UiRes.string.pin_enter_title)
                        },
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = when (state.localAuthType) {
                            LocalAuthType.CREATE_PIN ->
                                stringResource(
                                    UiRes.string.pin_create_description
                                )

                            LocalAuthType.CONFIRM_PIN ->
                                stringResource(
                                    UiRes.string.pin_confirm_description
                                )

                            LocalAuthType.ENTER_PIN ->
                                stringResource(
                                    UiRes.string.pin_enter_description
                                )
                        },
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 32.dp
                    )
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                when (state.localAuthType) {

                    LocalAuthType.CREATE_PIN -> {
                        DefaultTextField(
                            value = state.pinCode,
                            header = stringResource(
                                UiRes.string.pin_input
                            ),
                            placeholder = stringResource(
                                UiRes.string.pin_input_placeholder
                            ),
                            onValueChange = { value ->
                                if (
                                    value.length <= 6 &&
                                    value.all(Char::isDigit)
                                ) {
                                    onAction(
                                        LocalAuthAction.OnPinChanged(value)
                                    )
                                }
                            },
                            keyboardType = KeyboardType.NumberPassword,
                            error = state.error?.asString() ?: ""
                        )
                    }

                    LocalAuthType.CONFIRM_PIN -> {
                        DefaultTextField(
                            value = state.pinCodeConfirm,
                            header = stringResource(
                                UiRes.string.pin_confirm_input
                            ),
                            placeholder = stringResource(
                                UiRes.string.pin_confirm_input_placeholder
                            ),
                            onValueChange = { value ->
                                if (
                                    value.length <= 6 &&
                                    value.all(Char::isDigit)
                                ) {
                                    onAction(
                                        LocalAuthAction.OnConfirmPinChanged(
                                            value
                                        )
                                    )
                                }
                            },
                            keyboardType = KeyboardType.NumberPassword,
                            error = state.error?.asString() ?: ""
                        )
                    }

                    LocalAuthType.ENTER_PIN -> {
                        DefaultTextField(
                            value = state.pinCode,
                            header = stringResource(
                                UiRes.string.pin_input
                            ),
                            placeholder = stringResource(
                                UiRes.string.pin_input_placeholder
                            ),
                            onValueChange = { value ->
                                if (
                                    value.length <= 6 &&
                                    value.all(Char::isDigit)
                                ) {
                                    onAction(
                                        LocalAuthAction.OnPinChanged(value)
                                    )
                                }
                            },
                            keyboardType = KeyboardType.NumberPassword,
                            error = state.error?.asString() ?: ""
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onAction(
                            LocalAuthAction.OnCheckPinClicked
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = state.isFormValid && !state.isLoading,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = when (state.localAuthType) {
                            LocalAuthType.CREATE_PIN,
                            LocalAuthType.ENTER_PIN ->
                                stringResource(
                                    UiRes.string.continue_button
                                )

                            LocalAuthType.CONFIRM_PIN ->
                                stringResource(
                                    UiRes.string.confirm_button
                                )
                        },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.7f
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(
    name = "Create PIN",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LocalAuthScreenRootCreatePinPreview() {
    AppTheme {
        LocalAuthScreenRoot(
            state = LocalAuthState(
                localAuthType = LocalAuthType.CREATE_PIN,
                pinCode = "1234",
                isFormValid = true
            ),
            onAction = {}
        )
    }
}

@Preview(
    name = "Confirm PIN",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LocalAuthScreenRootConfirmPinPreview() {
    AppTheme {
        LocalAuthScreenRoot(
            state = LocalAuthState(
                localAuthType = LocalAuthType.CONFIRM_PIN,
                pinCode = "1234",
                pinCodeConfirm = "1234",
                isFormValid = true
            ),
            onAction = {}
        )
    }
}

@Preview(
    name = "Enter PIN",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LocalAuthScreenRootEnterPinPreview() {
    AppTheme {
        LocalAuthScreenRoot(
            state = LocalAuthState(
                localAuthType = LocalAuthType.ENTER_PIN,
                pinCode = "1234",
                isFormValid = true
            ),
            onAction = {}
        )
    }
}

@Preview(
    name = "Incorrect PIN",
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun LocalAuthScreenRootErrorPreview() {
    AppTheme {
        LocalAuthScreenRoot(
            state = LocalAuthState(
                localAuthType = LocalAuthType.ENTER_PIN,
                pinCode = "1234",
                isFormValid = true,
                error = UiText.Resource(
                    UiRes.string.error_pin_incorrect
                )
            ),
            onAction = {}
        )
    }
}
