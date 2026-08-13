package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import androidx.compose.foundation.layout.padding
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.appointment_status_cancelled
import medicalplatformmobile.shared.generated.resources.appointment_status_completed
import medicalplatformmobile.shared.generated.resources.appointment_status_confirmed
import medicalplatformmobile.shared.generated.resources.appointment_status_pending
import medicalplatformmobile.shared.generated.resources.appointment_status_rejected
import medicalplatformmobile.shared.generated.resources.appointment_status_unknown
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock

@Composable
fun AppointmentCard(
    appointment: Appointment,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DateBadge(date = appointment.date)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                val time = appointment.time.let { time ->
                    val hour = time.hour.toString().padStart(2, '0')
                    val minute = time.minute.toString().padStart(2, '0')
                    "$hour:$minute"
                }

                if(appointment.doctor != null){
                    Text(
                        text = "${appointment.doctor.title} ${appointment.doctor.firstName} ${appointment.doctor.lastName}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                if(appointment.patient != null){
                    Text(
                        text = "${appointment.patient.title} ${appointment.patient.firstName} ${appointment.patient.lastName}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            StatusLabel(status = appointment.status)
        }
    }
}

@Composable
private fun DateBadge(date: LocalDate) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date.month.name.take(3).lowercase()
                .replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = date.day.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun StatusLabel(status: AppointmentStatus) {
    val (labelRes, color) = when (status) {
        AppointmentStatus.PENDING -> UiRes.string.appointment_status_pending to MaterialTheme.colorScheme.tertiary
        AppointmentStatus.CONFIRMED -> UiRes.string.appointment_status_confirmed to MaterialTheme.colorScheme.primary
        AppointmentStatus.REJECTED -> UiRes.string.appointment_status_rejected to MaterialTheme.colorScheme.error
        AppointmentStatus.CANCELLED -> UiRes.string.appointment_status_cancelled to MaterialTheme.colorScheme.error
        AppointmentStatus.COMPLETED -> UiRes.string.appointment_status_completed to MaterialTheme.colorScheme.secondary
        AppointmentStatus.UNKNOWN -> UiRes.string.appointment_status_unknown to MaterialTheme.colorScheme.outline
    }
    Text(
        text = stringResource(labelRes),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Medium,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
private fun AppointmentCardPreview() {

    val sampleSpecialization = Specialization(id = "spec1", name = "Cardiology")

    val sampleDoctorUser = User(
        id = "u1",
        email = "sarah.chen@example.com",
        firstName = "Sarah",
        lastName = "Chen",
        role = "DOCTOR"
    )

    val appointment = Appointment(
        id = "1",
        doctor = sampleDoctorUser,
        status = AppointmentStatus.CONFIRMED,
        note = "",
        date = LocalDate(2026, 7, 18),
        time = LocalTime(10, 30),
        createdAt = Clock.System.now(),
        updatedAt = Clock.System.now()
    )

    AppTheme {
        Surface {
            AppointmentCard(
                appointment = appointment,
                modifier = Modifier.padding(16.dp),
                onClick = {}
            )
        }
    }
}