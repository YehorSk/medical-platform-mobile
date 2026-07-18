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
import com.yehorsk.medical_platform_mobile.core.domain.model.Doctor
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.domain.model.Workplace
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_forward_24px
import medicalplatformmobile.shared.generated.resources.star_rate_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun DoctorCard(
    user: User,
    modifier: Modifier = Modifier,
    rating: Float = 4.9f,
    reviewsCount: Int = 128,
    onClick: () -> Unit
) {
    require(user.getUserRole() == UserRole.DOCTOR) {
        "DoctorCard can only display users with DOCTOR role."
    }

    val doctor = user.doctor

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                        text = "${user.firstName.first()}${user.lastName.first()}",
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
                    text = "${user.title} ${user.firstName} ${user.lastName}".trim(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = listOfNotNull(
                        doctor?.specialization?.name,
                        doctor?.workplace?.name
                    ).joinToString(" · "),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(UiRes.drawable.star_rate_24px),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = "$reviewsCount reviews",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                painter = painterResource(UiRes.drawable.arrow_forward_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DoctorCardPreview() {
    MaterialTheme {
        DoctorCard(
            modifier = Modifier.padding(16.dp),
            user = User(
                id = "1",
                email = "sarah@example.com",
                firstName = "Sarah",
                lastName = "Chen",
                role = "DOCTOR",
                title = "Dr.",
                doctor = Doctor(
                    id = 1,
                    userId = 1,
                    specialization = Specialization(
                        id = "1",
                        name = "Cardiology"
                    ),
                    workplace = Workplace(
                        id = 1,
                        name = "St. Mary Medical Center",
                        address = "123 Main St",
                        phone = "123456789",
                        city = "New York"
                    )
                )
            ),
            rating = 4.9f,
            reviewsCount = 128,
            onClick = {}
        )
    }
}
