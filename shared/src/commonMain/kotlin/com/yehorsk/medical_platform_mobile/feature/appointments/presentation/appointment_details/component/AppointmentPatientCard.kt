package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentDoctor
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentPatient
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.BloodType
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Gender
import com.yehorsk.medical_platform_mobile.util.calculateAge
import com.yehorsk.medical_platform_mobile.util.toDisplayName
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.time.Instant

@Composable
fun AppointmentPatientCard(
    appointment: Appointment,
    modifier: Modifier = Modifier
) {
    val patient = appointment.patient ?: return

    val age = remember(patient.dateOfBirth) {
        calculateAge(patient.dateOfBirth)
    }

    val gender = patient.gender.toDisplayName()
    val bloodType = patient.bloodType.toDisplayName()

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = patient.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PatientMetaItem(
                            label = "Age",
                            value = age?.toString() ?: "—"
                        )

                        PatientMetaItem(
                            label = "Sex",
                            value = gender.asString()
                        )

                        PatientMetaItem(
                            label = "Blood",
                            value = bloodType.asString()
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Patient badge
                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.onPrimary.copy(
                        alpha = 0.16f
                    )
                ) {
                    Text(
                        text = "Patient",
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 7.dp
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun PatientMetaItem(
    label: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = 0.65f
            )
        )

        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun AppointmentPatientCardPreview() {
    MaterialTheme {
        AppointmentPatientCard(
            appointment = Appointment(
                id = "appointment-1",
                doctor = AppointmentDoctor(
                    id = "doctor-1",
                    firstName = "Sarah",
                    lastName = "Smith",
                    title = "Dr.",
                    specialization = "Cardiology"
                ),
                patient = AppointmentPatient(
                    id = "patient-1",
                    firstName = "James",
                    lastName = "Wilson",
                    title = "",
                    dateOfBirth = "1990-09-19",
                    gender = Gender.MALE,
                    bloodType = BloodType.A_POSITIVE
                ),
                status = AppointmentStatus.CONFIRMED,
                note = "Regular checkup",
                date = LocalDate(2026, 9, 20),
                time = LocalTime(10, 30),
                createdAt = Instant.parse("2026-09-01T10:00:00Z"),
                updatedAt = Instant.parse("2026-09-01T10:00:00Z")
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}