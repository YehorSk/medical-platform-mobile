package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.kizitonwose.calendar.core.now
import com.yehorsk.medical_platform_mobile.util.formatDate
import com.yehorsk.medical_platform_mobile.util.formatDateTime
import kotlinx.datetime.*

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DefaultDateTimeField(
//    modifier: Modifier = Modifier,
//    value: String?,
//    header: String? = null,
//    placeholder: String = "",
//    leadingIcon: Painter? = null,
//    leadingIconDescr: String = "",
//    onValueChange: (LocalDateTime) -> Unit,
//    includeTime: Boolean = true,
//    maxDate: LocalDate? = LocalDate.now(),
//    error: String = ""
//) {
//    var showDialog by remember { mutableStateOf(false) }
//
//    header?.let {
//        Text(it, fontWeight = FontWeight.Medium)
//        Spacer(modifier = Modifier.height(8.dp))
//    }
//
//    val displayText = value?.let { formatDateTime(it, includeTime) } ?: ""
//
//    Box(modifier = modifier.fillMaxWidth()) {
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = displayText,
//            onValueChange = {}, // no-op — text is derived, not typed
//            readOnly = true,
//            enabled = false, // disables focus/cursor while keeping custom colors below
//            leadingIcon = leadingIcon?.let { painter ->
//                { Icon(painter = painter, contentDescription = leadingIconDescr) }
//            },
//            placeholder = { Text(text = placeholder) },
//            isError = error.isNotBlank(),
//            supportingText = {
//                if (error.isNotBlank()) {
//                    Text(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = error,
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//            },
//            shape = RoundedCornerShape(12.dp),
//            colors = OutlinedTextFieldDefaults.colors(
//                unfocusedContainerColor = Color(0xFFF5F5F5),
//                focusedContainerColor = Color(0xFFF5F5F5),
//                disabledContainerColor = Color(0xFFF5F5F5),
//                unfocusedBorderColor = Color.Transparent,
//                focusedBorderColor = Color(0xFF2B5CE6),
//                disabledBorderColor = Color.Transparent,
//                disabledTextColor = MaterialTheme.colorScheme.onSurface,
//                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
//            ),
//            singleLine = true
//        )
//        // Transparent overlay to capture taps, since the field itself is disabled
//        Box(
//            modifier = Modifier
//                .matchParentSize()
//                .clickable(
//                    interactionSource = remember { MutableInteractionSource() },
//                    indication = null
//                ) { showDialog = true }
//        )
//    }
//
//    if (showDialog) {
//        DateTimePickerDialog(
//            initialValue = value ?: LocalDateTime.now(),
//            includeTime = includeTime,
//            maxDate = maxDate,
//            onDismiss = { showDialog = false },
//            onConfirm = {
//                onValueChange(it)
//                showDialog = false
//            }
//        )
//    }
//}