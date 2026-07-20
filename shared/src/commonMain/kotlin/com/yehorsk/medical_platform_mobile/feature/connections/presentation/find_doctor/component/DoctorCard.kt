package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.Clinic
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.Workplace
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_forward_ios_24px
import medicalplatformmobile.shared.generated.resources.star_rate_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun DoctorCard(
    doctor: Doctor,
    modifier: Modifier = Modifier,
    rating: Float = 4.9f,
    reviewsCount: Int = 128,
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

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "${doctor.user!!.firstName.first()}${doctor.user.lastName.first()}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${doctor.user!!.title} ${doctor.user.firstName} ${doctor.user.lastName}".trim(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = listOfNotNull(
                        doctor.specialization?.name,
                        doctor.workplace?.clinic?.name
                    ).joinToString(" · "),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

//                Spacer(Modifier.height(8.dp))

//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        painter = painterResource(UiRes.drawable.star_rate_24px),
//                        contentDescription = null,
//                        modifier = Modifier.size(16.dp),
//                        tint = MaterialTheme.colorScheme.tertiary
//                    )
//
//                    Spacer(Modifier.width(4.dp))
//
//                    Text(
//                        text = rating.toString(),
//                        style = MaterialTheme.typography.labelLarge,
//                        fontWeight = FontWeight.SemiBold
//                    )
//
//                    Spacer(Modifier.width(8.dp))
//
//                    Text(
//                        text = "$reviewsCount reviews",
//                        style = MaterialTheme.typography.bodySmall,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant
//                    )
//                }
            }

            Icon(
                painter = painterResource(UiRes.drawable.arrow_forward_ios_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
        HorizontalDivider()
    }
}


@Preview(showBackground = true)
@Composable
fun DoctorCardPreview() {
    MaterialTheme {
        DoctorCard(
            doctor = Doctor(
                id = "cc75b982-0f3e-45f0-a601-23278c2e128b",
                licenseNumber = "LIC-001-1784566413864",
                user = User(
                    id = "160eccf4-ed8d-4970-bea4-45678e87d7d6",
                    email = "doctor1@example.com",
                    firstName = "James",
                    lastName = "Smith",
                    role = "DOCTOR",
                    title = "Dr.",
                    phone = "+1-555-0123",
                    address = "123 Main Street",
                    emergencyContactName = "Anna Smith",
                    emergencyContactPhone = "+1-555-0456"
                ),
                approvedBy = null,
                approved = true,
                description = "Experienced cardiologist providing quality patient care.",
                specialization = Specialization(
                    id = "92dac9ca-ae7f-4b82-b292-af565ddc2a04",
                    name = "Cardiology"
                ),
                workplace = Workplace(
                    id = "2b375d92-1d47-4655-afeb-f4388d900ff1",
                    roomNumber = "Room 100",
                    clinic = Clinic(
                        id = "6c807210-d093-4506-9c6c-d63c5b71950d",
                        name = "Central Medical Clinic",
                        address = "123 Main Street",
                        phone = "+1-555-0123",
                        city = "New York"
                    )
                ),
                createdAt = "2026-07-20T16:53:33.883072Z",
                updatedAt = "2026-07-20T16:53:33.883072Z",
                approvedAt = "2026-07-20T16:53:33.868806Z"
            ),
            onClick = {},
            rating = 10f,
            reviewsCount = 10
        )
    }
}
