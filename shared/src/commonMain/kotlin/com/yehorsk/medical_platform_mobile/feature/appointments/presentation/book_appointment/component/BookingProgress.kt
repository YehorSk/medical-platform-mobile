package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookingStep
import com.yehorsk.medical_platform_mobile.util.titleRes
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.check_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun BookingProgress(
    currentStep: BookingStep,
    modifier: Modifier = Modifier,
) {
    val steps = BookingStep.entries
    val colors = MaterialTheme.colorScheme

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
            verticalAlignment = Alignment.Top,
        ) {
            steps.forEachIndexed { index, step ->

                val isCompleted = index < currentStep.ordinal
                val isCurrent = index == currentStep.ordinal

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        color = when {
                            isCompleted || isCurrent -> colors.primary
                            else -> colors.surfaceVariant
                        },
                        contentColor = when {
                            isCompleted || isCurrent -> colors.onPrimary
                            else -> colors.onSurfaceVariant
                        }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (isCompleted) {
                                Icon(
                                    painter = painterResource(UiRes.drawable.check_24px),
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            } else {
                                Text(
                                    text = "${index + 1}",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = step.titleRes(),
                        style = MaterialTheme.typography.labelMedium,
                        color = when {
                            isCurrent -> colors.primary
                            isCompleted -> colors.onSurface
                            else -> colors.onSurfaceVariant
                        }
                    )
                }

                if (index != steps.lastIndex) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp, vertical = 17.dp)
                            .height(2.dp)
                            .background(
                                color = if (isCompleted)
                                    colors.primary
                                else
                                    colors.outlineVariant
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BookingProgressDatePreview(){
    AppTheme {
        BookingProgress(
            currentStep = BookingStep.Date
        )
    }
}

@Preview
@Composable
fun BookingProgressTimePreview(){
    AppTheme {
        BookingProgress(
            currentStep = BookingStep.Time
        )
    }
}