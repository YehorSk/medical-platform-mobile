package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.add_24px
import medicalplatformmobile.shared.generated.resources.cancel_btn
import medicalplatformmobile.shared.generated.resources.check_24px
import medicalplatformmobile.shared.generated.resources.complete_appointment
import medicalplatformmobile.shared.generated.resources.create_medical_record
import medicalplatformmobile.shared.generated.resources.create_medical_record_prompt
import medicalplatformmobile.shared.generated.resources.just_mark_complete
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompleteAppointmentBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onCreateMedicalRecordClicked: () -> Unit,
    onJustMarkCompleteClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = { },
        content = {
            CompleteAppointmentBottomSheetContent(
                modifier = modifier,
                onCreateMedicalRecordClicked = onCreateMedicalRecordClicked,
                onJustMarkCompleteClicked = onJustMarkCompleteClicked,
                onCancelClicked = onCancelClicked,
            )
        }
    )
}

@Composable
fun CompleteAppointmentBottomSheetContent(
    modifier: Modifier = Modifier,
    onCreateMedicalRecordClicked: () -> Unit,
    onJustMarkCompleteClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
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
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Status icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(UiRes.drawable.check_24px),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(UiRes.string.complete_appointment),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(UiRes.string.create_medical_record_prompt),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(24.dp))

            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCreateMedicalRecordClicked,
                text = stringResource(UiRes.string.create_medical_record),
                leadingIcon = painterResource(UiRes.drawable.add_24px),
                color = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(modifier = Modifier.height(8.dp))

            DefaultButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onJustMarkCompleteClicked,
                color = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                text = stringResource(UiRes.string.just_mark_complete),
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onCancelClicked) {
                Text(
                    text = stringResource(UiRes.string.cancel_btn),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CompleteAppointmentBottomSheetContentPreview() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            CompleteAppointmentBottomSheetContent(
                onCreateMedicalRecordClicked = {},
                onJustMarkCompleteClicked = {},
                onCancelClicked = {},
            )
        }
    }
}