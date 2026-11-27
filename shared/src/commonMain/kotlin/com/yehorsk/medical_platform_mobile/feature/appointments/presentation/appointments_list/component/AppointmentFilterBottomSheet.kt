package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultFilterChipRow
import com.yehorsk.medical_platform_mobile.core.ui.components.textfields.DefaultDatePicker
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentsFilter
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.SortDirection
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.LocalDate
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.appointment_status
import medicalplatformmobile.shared.generated.resources.appointment_status_cancelled
import medicalplatformmobile.shared.generated.resources.appointment_status_completed
import medicalplatformmobile.shared.generated.resources.appointment_status_confirmed
import medicalplatformmobile.shared.generated.resources.appointment_status_pending
import medicalplatformmobile.shared.generated.resources.appointment_status_rejected
import medicalplatformmobile.shared.generated.resources.appointment_status_unknown
import medicalplatformmobile.shared.generated.resources.apply_filters
import medicalplatformmobile.shared.generated.resources.calendar_month_24px
import medicalplatformmobile.shared.generated.resources.clear_date
import medicalplatformmobile.shared.generated.resources.clear_filters
import medicalplatformmobile.shared.generated.resources.close_24px
import medicalplatformmobile.shared.generated.resources.date_from
import medicalplatformmobile.shared.generated.resources.date_range
import medicalplatformmobile.shared.generated.resources.date_range_invalid
import medicalplatformmobile.shared.generated.resources.date_to
import medicalplatformmobile.shared.generated.resources.filter_appointments
import medicalplatformmobile.shared.generated.resources.sort_newest_first
import medicalplatformmobile.shared.generated.resources.sort_oldest_first
import medicalplatformmobile.shared.generated.resources.sort_order
import medicalplatformmobile.shared.generated.resources.upcoming_only
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// UNKNOWN is only a fallback for unexpected backend values, so it can't be filtered on
private val filterableStatuses = AppointmentStatus.entries - AppointmentStatus.UNKNOWN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentFilterBottomSheet(
    modifier: Modifier = Modifier,
    filter: AppointmentsFilter,
    onDismiss: () -> Unit,
    onStatusClicked: (AppointmentStatus) -> Unit,
    onFromDateChanged: (LocalDate?) -> Unit,
    onToDateChanged: (LocalDate?) -> Unit,
    onUpcomingToggled: (Boolean) -> Unit,
    onSortDirectionChanged: (SortDirection) -> Unit,
    onClearClicked: () -> Unit,
    onApplyClicked: () -> Unit,
){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
    ) {
        AppointmentFilterBottomSheetContent(
            filter = filter,
            onStatusClicked = onStatusClicked,
            onFromDateChanged = onFromDateChanged,
            onToDateChanged = onToDateChanged,
            onUpcomingToggled = onUpcomingToggled,
            onSortDirectionChanged = onSortDirectionChanged,
            onClearClicked = onClearClicked,
            onApplyClicked = onApplyClicked
        )
    }
}

@Composable
fun AppointmentFilterBottomSheetContent(
    modifier: Modifier = Modifier,
    filter: AppointmentsFilter,
    onStatusClicked: (AppointmentStatus) -> Unit,
    onFromDateChanged: (LocalDate?) -> Unit,
    onToDateChanged: (LocalDate?) -> Unit,
    onUpcomingToggled: (Boolean) -> Unit,
    onSortDirectionChanged: (SortDirection) -> Unit,
    onClearClicked: () -> Unit,
    onApplyClicked: () -> Unit,
){
    BoxWithConstraints {
        val widthModifier = if (this.maxWidth < 400.dp) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.width(640.dp)
        }
        Column(
            modifier = modifier
                .then(widthModifier)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(UiRes.string.filter_appointments),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                TextButton(onClick = onClearClicked) {
                    Text(stringResource(UiRes.string.clear_filters))
                }
            }
            Column(
                modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp),
            ) {
                SectionTitle(stringResource(UiRes.string.appointment_status))
                DefaultFilterChipRow(
                    items = filterableStatuses,
                    isSelected = { it in filter.statuses },
                    label = { stringResource(it.label()) },
                    onItemClick = onStatusClicked
                )

                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(stringResource(UiRes.string.date_range))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ClearableDateField(
                        modifier = Modifier.weight(1f),
                        value = filter.from,
                        placeholder = stringResource(UiRes.string.date_from),
                        maxDate = filter.to,
                        onValueChange = onFromDateChanged
                    )
                    ClearableDateField(
                        modifier = Modifier.weight(1f),
                        value = filter.to,
                        placeholder = stringResource(UiRes.string.date_to),
                        minDate = filter.from,
                        onValueChange = onToDateChanged
                    )
                }
                if (filter.hasInvalidRange) {
                    Text(
                        text = stringResource(UiRes.string.date_range_invalid),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        text = stringResource(UiRes.string.upcoming_only)
                    )
                    Switch(
                        checked = filter.upcoming,
                        onCheckedChange = onUpcomingToggled
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                SectionTitle(stringResource(UiRes.string.sort_order))
                DefaultFilterChipRow(
                    items = SortDirection.entries,
                    isSelected = { it == filter.sortDirection },
                    label = {
                        stringResource(
                            when (it) {
                                SortDirection.ASC -> UiRes.string.sort_oldest_first
                                SortDirection.DESC -> UiRes.string.sort_newest_first
                            }
                        )
                    },
                    onItemClick = onSortDirectionChanged
                )

                Spacer(modifier = Modifier.height(24.dp))
                DefaultButton(
                    onClick = onApplyClicked,
                    enabled = !filter.hasInvalidRange,
                    text = stringResource(UiRes.string.apply_filters)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        text = text
    )
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ClearableDateField(
    modifier: Modifier = Modifier,
    value: LocalDate?,
    placeholder: String,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onValueChange: (LocalDate?) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        DefaultDatePicker(
            modifier = Modifier.weight(1f),
            value = value,
            placeholder = placeholder,
            trailingIcon = painterResource(UiRes.drawable.calendar_month_24px),
            minDate = minDate,
            maxDate = maxDate,
            onValueChange = onValueChange
        )
        if (value != null) {
            IconButton(onClick = { onValueChange(null) }) {
                Icon(
                    painter = painterResource(UiRes.drawable.close_24px),
                    contentDescription = stringResource(UiRes.string.clear_date)
                )
            }
        }
    }
}

private fun AppointmentStatus.label(): StringResource = when (this) {
    AppointmentStatus.PENDING -> UiRes.string.appointment_status_pending
    AppointmentStatus.CONFIRMED -> UiRes.string.appointment_status_confirmed
    AppointmentStatus.REJECTED -> UiRes.string.appointment_status_rejected
    AppointmentStatus.CANCELLED -> UiRes.string.appointment_status_cancelled
    AppointmentStatus.COMPLETED -> UiRes.string.appointment_status_completed
    AppointmentStatus.UNKNOWN -> UiRes.string.appointment_status_unknown
}

@Preview
@Composable
private fun AppointmentFilterBottomSheetContentPreview() {
    AppTheme {
        AppointmentFilterBottomSheetContent(
            filter = AppointmentsFilter(statuses = setOf(AppointmentStatus.CONFIRMED), upcoming = true),
            onStatusClicked = {},
            onFromDateChanged = {},
            onToDateChanged = {},
            onUpcomingToggled = {},
            onSortDirectionChanged = {},
            onClearClicked = {},
            onApplyClicked = {}
        )
    }
}
