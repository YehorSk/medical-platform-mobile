package com.yehorsk.medical_platform_mobile.feature.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsAction
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.component.SettingsMainHeader
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    navigateToProfilePage: () -> Unit
){
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                is SettingsAction.GoToProfileScreen -> {
                    navigateToProfilePage()
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
            state = state,
            navigateToProfilePage = { onAction(SettingsAction.GoToProfileScreen) }
        )
    }
}