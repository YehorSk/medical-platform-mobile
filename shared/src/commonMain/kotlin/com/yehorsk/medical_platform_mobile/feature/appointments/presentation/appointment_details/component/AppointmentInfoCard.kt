package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.util.formatShortDateTime
import com.yehorsk.medical_platform_mobile.util.formatTime
import com.yehorsk.medical_platform_mobile.util.toColor
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.*
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.calendar_month_24px
import org.jetbrains.compose.resources.painterResource
import kotlin.time.Clock

@Composable
fun AppointmentInfoCard(
    appointment: Appointment,
    modifier: Modifier = Modifier
){
    val statusColor = appointment.status.toColor()

    val dateText = formatShortDateTime(appointment.date.toString())

    val timeText = appointment.time.let { t ->
        val hh = t.hour.toString().padStart(2, '0')
        val mm = t.minute.toString().padStart(2, '0')
        val ss = t.second.toString().padStart(2, '0')
        formatTime("$hh:$mm:$ss")
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header: icon + title + status pill
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(UiRes.drawable.calendar_month_24px),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    if(appointment.specialization.isNotEmpty()){
                        Text(
                            text = appointment.specialization,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(statusColor)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = appointment.status.name
                                .lowercase()
                                .replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoBox(
                    label = "Date",
                    value = dateText,
                    modifier = Modifier.weight(1f)
                )
                InfoBox(
                    label = "Time",
                    value = timeText,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun InfoBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White.copy(alpha = 0.15f))
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppointmentGradientCardPreview() {
    val now = Clock.System.now()

    val sampleDoctorUser = User(
        id = "u1",
        email = "sarah.chen@example.com",
        firstName = "Sarah",
        lastName = "Chen",
        role = "DOCTOR"
    )

    val appointment = Appointment(
        id = "1",
        userDoctor = sampleDoctorUser,
        status = AppointmentStatus.PENDING,
        specialization = "Dermatologist",
        note = "",
        date = LocalDate(2026, 7, 22),
        time = LocalTime(14, 0),
        createdAt = now,
        updatedAt = now
    )

    AppTheme {
        Surface {
            AppointmentInfoCard(
                appointment = appointment,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}