package com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultButton
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.component.SettingsMainHeader
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.component.WeeklyScheduleList
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel.DoctorScheduleAction
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel.DoctorScheduleUiState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.doctor_schedule.viewmodel.DoctorScheduleViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DoctorScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: DoctorScheduleViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    DoctorScheduleScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when(action){
                DoctorScheduleAction.OnGoBackClicked -> onGoBackClicked()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun DoctorScheduleScreenRoot(
    modifier: Modifier = Modifier,
    state: DoctorScheduleUiState,
    onAction: (DoctorScheduleAction) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SettingsMainHeader(
            showGoBackButton = true,
            showUserData = false,
            onGoBackButtonClicked = {
                onAction(DoctorScheduleAction.OnGoBackClicked)
            }
        )
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            WeeklyScheduleList(
                schedule = state.schedule,
                modifier = Modifier.weight(1f, fill = false),
                onDayChange = { onAction(DoctorScheduleAction.DayChanged(it)) },
                onCopyToWeekdays = { onAction(DoctorScheduleAction.CopyToWeekdays(it)) },
            )
            Spacer(Modifier.height(12.dp))
            DefaultButton(
                text = "Save schedule",
                onClick = {
                    onAction(DoctorScheduleAction.OnSaveClicked)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}