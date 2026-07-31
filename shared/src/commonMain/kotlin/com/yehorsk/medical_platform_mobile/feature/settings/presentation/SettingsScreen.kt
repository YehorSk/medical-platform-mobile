package com.yehorsk.medical_platform_mobile.feature.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.util.ObserveAsEvents
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.component.SettingsListItem
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsAction
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.component.SettingsMainHeader
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsScreenEvent
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.logout
import medicalplatformmobile.shared.generated.resources.settings_account_header
import medicalplatformmobile.shared.generated.resources.settings_change_password
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    navigateToProfilePage: () -> Unit,
    navigateToUpdatePwdPage: () -> Unit,
    onLogoutClicked: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when(event){
            SettingsScreenEvent.OnLogoutSuccess -> onLogoutClicked()
        }
    }

    SettingsScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is SettingsAction.GoToProfileScreen -> {
                    navigateToProfilePage()
                }
                is SettingsAction.GoToUpdatePwdScreen -> {
                    navigateToUpdatePwdPage()
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun SettingsScreenRoot(
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
            state = state
        )
        SettingsList(
            modifier = Modifier.weight(1f),
            onAction = onAction
        )
    }
}

@Composable
fun SettingsList(
    modifier: Modifier = Modifier,
    onAction: (SettingsAction) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            SettingsListItem(
                text = stringResource(UiRes.string.settings_account_header),
                onClick = { onAction(SettingsAction.GoToProfileScreen) }
            )
        }
        item {
            SettingsListItem(
                text = stringResource(UiRes.string.settings_change_password),
                onClick = { onAction(SettingsAction.GoToUpdatePwdScreen) }
            )
        }
        item {
            SettingsListItem(
                text = stringResource(UiRes.string.logout),
                onClick = { onAction(SettingsAction.OnLogoutClicked) }
            )
        }
    }
}