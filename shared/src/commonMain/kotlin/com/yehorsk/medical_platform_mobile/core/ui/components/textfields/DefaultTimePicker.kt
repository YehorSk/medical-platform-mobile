package com.yehorsk.medical_platform_mobile.core.ui.components.textfields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import org.jetbrains.compose.resources.painterResource
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTimePicker(
    modifier: Modifier = Modifier,
    value: LocalTime = LocalTime.now(),
    header: String? = null,
    placeholder: String = "",
    leadingIcon: Painter? = null,
    leadingIconDescr: String = "",
    trailingIcon: Painter? = null,
    trailingIconDescr: String = "Pick time",
    onValueChange: (LocalTime) -> Unit,
    error: String = "",
    is24Hour: Boolean = true,
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val displayFormatter = remember {
        DateTimeFormatter.ofPattern("HH:mm", Locale.ROOT)
    }
    DefaultTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value.format(displayFormatter),
        header = header,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        leadingIconDescr = leadingIconDescr,
        trailingIcon = trailingIcon,
        trailingIconDescr = trailingIconDescr,
        onTrailingIconClick = { showDialog = true },
        onValueChange = {},
        error = error,
        readOnly = true,
        enabled = false,
        onClick = { showDialog = true }
    )

    if (showDialog) {
        val timePickerState = rememberTimePickerState(
            initialHour = value.hour,
            initialMinute = value.minute,
            is24Hour = is24Hour,
        )

        TimePickerDialog(
            onDismissRequest = { showDialog = false },
            title = {
                header?.let {
                    Text(
                        text = it
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onValueChange(LocalTime.of(timePickerState.hour, timePickerState.minute))
                        showDialog = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancel") }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
private fun DefaultTimePickerPreview() {
    AppTheme {
        DefaultTimePicker(
            value = LocalTime.of(9, 0),
            header = "Start time",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true, name = "24-hour format")
@Composable
private fun DefaultTimePicker24HourPreview() {
    AppTheme {
        DefaultTimePicker(
            value = LocalTime.of(17, 30),
            header = "End time",
            is24Hour = true,
            onValueChange = {},
        )
    }
}