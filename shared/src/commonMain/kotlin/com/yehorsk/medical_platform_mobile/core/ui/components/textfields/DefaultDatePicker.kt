package com.yehorsk.medical_platform_mobile.core.ui.components.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import com.kizitonwose.calendar.core.now
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.atStartOfDayIn
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.calendar_month_24px
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultDatePicker(
    modifier: Modifier = Modifier,
    value: LocalDate = LocalDate.now(),
    header: String? = null,
    placeholder: String = "",
    leadingIcon: Painter? = null,
    leadingIconDescr: String = "",
    trailingIcon: Painter? = null,
    trailingIconDescr: String = "Pick date",
    onValueChange: (LocalDate) -> Unit,
    error: String = "",
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clickable(enabled = true){
                showDialog = true
            }
    ){
        DefaultTextField(
            modifier = modifier.fillMaxWidth(),
            value = value.toString(),
            header = header,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            leadingIconDescr = leadingIconDescr,
            trailingIcon = trailingIcon,
            trailingIconDescr = trailingIconDescr,
            onTrailingIconClick = { showDialog = true },
            onValueChange = {},
            error = error
        )
    }

    if (showDialog) {
        val initialMillis = value
            .atStartOfDayIn(TimeZone.UTC)
            .toEpochMilliseconds()

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialMillis,
            selectableDates = remember(minDate, maxDate) {
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        val date = Instant.fromEpochMilliseconds(utcTimeMillis)
                            .toLocalDateTime(TimeZone.UTC).date
                        if (minDate != null && date < minDate) return false
                        if (maxDate != null && date > maxDate) return false
                        return true
                    }
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val picked = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.UTC).date
                            onValueChange(picked)
                        }
                        showDialog = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
fun DefaultDatePickerPreview(){
    AppTheme {
        DefaultDatePicker(
            onValueChange = {},
            trailingIcon = painterResource(UiRes.drawable.calendar_month_24px)
        )
    }
}