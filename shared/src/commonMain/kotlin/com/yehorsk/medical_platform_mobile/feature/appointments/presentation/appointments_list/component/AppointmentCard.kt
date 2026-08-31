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
import com.yehorsk.medical_platform_mobile.LocalUserRole
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentDoctor
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components.StatusBox
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.Clock

@Composable
fun AppointmentCard(
    appointment: Appointment,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    role: UserRole
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

                if(role == UserRole.PATIENT && appointment.doctor != null){
                    Text(
                        text = "${appointment.doctor.title} ${appointment.doctor.firstName} ${appointment.doctor.lastName}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }else if(role == UserRole.DOCTOR && appointment.patient != null){
                    Text(
                        text = "${appointment.patient.title} ${appointment.patient.firstName} ${appointment.patient.lastName}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }else {
                    Text(
                        text = "No Data",
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

            StatusBox(
                status = appointment.status
            )
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

@Preview(showBackground = true)
@Composable
private fun AppointmentCardPreview() {

    val appointment = Appointment(
        id = "1",
        doctor = AppointmentDoctor(
            id = "",
            firstName = "John",
            lastName = "Smith",
            title = "Bc.",
            specialization = "Surgeon"
        ),

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
                onClick = {},
                role = UserRole.PATIENT
            )
        }
    }
}