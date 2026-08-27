package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_patient.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.MedicalCardPatient
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.component.DoctorCard
import com.yehorsk.medical_platform_mobile.util.fakeDoctor
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_forward_ios_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun PatientCard(
    patient: MedicalCardPatient,
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

            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "${patient.firstName.first()}${patient.lastName.first()}",
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
                    text = "${patient.title} ${patient.firstName} ${patient.lastName}".trim(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
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
fun PatientCardPreview() {
    MaterialTheme {
        PatientCard(
            patient = MedicalCardPatient(
                id = "",
                firstName = "John",
                lastName = "Doe",
                title = "Bc."
            ),
            onClick = {},
        )
    }
}
