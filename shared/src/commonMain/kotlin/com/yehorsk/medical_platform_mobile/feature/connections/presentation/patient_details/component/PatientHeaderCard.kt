package com.yehorsk.medical_platform_mobile.feature.connections.presentation.patient_details.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme

@Composable
fun PatientHeaderCard(
    firstName: String,
    lastName: String,
    age: Int?,
    bloodType: String?,
    dateOfBirth: String?,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
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
                // Doctor info
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(68.dp),
                        shape = CircleShape,
                        color = Color.White.copy(alpha = .18f)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${firstName.firstOrNull() ?: '-'}${lastName.firstOrNull() ?: '-'}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "$firstName $lastName",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PatientInfoItem(
                        label = "Age",
                        value = age?.toString() ?: "-",
                        modifier = Modifier.weight(1f)
                    )

                    PatientInfoItem(
                        label = "Blood Type",
                        value = bloodType ?: "-",
                        modifier = Modifier.weight(1f)
                    )

                    PatientInfoItem(
                        label = "Date of Birth",
                        value = dateOfBirth ?: "-",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun PatientInfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White.copy(alpha = .15f)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 10.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = .8f)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PatientHeaderCardPreview(){
    AppTheme {
        PatientHeaderCard(
            firstName = "John",
            lastName = "Doe",
            age = 34,
            bloodType = "1+",
            dateOfBirth = "1.9.2026"
        )
    }
}