package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.TabToggleButton
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel.AppointmentsViewMode
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.appointments_view_calendar
import medicalplatformmobile.shared.generated.resources.appointments_view_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppointmentsViewTabs(
    modifier: Modifier = Modifier,
    selectedMode: AppointmentsViewMode,
    onModeSelected: (AppointmentsViewMode) -> Unit
){
    Box(
        modifier = modifier
            .background(Color(0xFFF0F0F0), RoundedCornerShape(50.dp))
            .padding(4.dp)
    ) {
        Row {
            TabToggleButton(
                label = stringResource(UiRes.string.appointments_view_calendar),
                selected = selectedMode == AppointmentsViewMode.CALENDAR,
                onClick = { onModeSelected(AppointmentsViewMode.CALENDAR) },
                modifier = Modifier.weight(1f)
            )
            TabToggleButton(
                label = stringResource(UiRes.string.appointments_view_list),
                selected = selectedMode == AppointmentsViewMode.LIST,
                onClick = { onModeSelected(AppointmentsViewMode.LIST) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
