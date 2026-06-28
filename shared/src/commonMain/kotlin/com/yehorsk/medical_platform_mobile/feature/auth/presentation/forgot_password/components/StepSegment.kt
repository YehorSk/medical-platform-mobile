package com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.forgot_password.viewmodel.PasswordResetStep
import com.yehorsk.theme.AppTheme

@Composable
fun StepIndicator(
    currentStep: PasswordResetStep,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PasswordResetStep.entries.forEach { step ->
            val isCurrent = step == currentStep
            val isDone = step.ordinal < currentStep.ordinal

            val color by animateColorAsState(
                targetValue = when {
                    isDone    -> MaterialTheme.colorScheme.outline
                    isCurrent -> MaterialTheme.colorScheme.secondary
                    else      -> MaterialTheme.colorScheme.outlineVariant
                },
                animationSpec = tween(durationMillis = 300),
                label = "segmentColor"
            )

            val width by animateDpAsState(
                targetValue = if (isCurrent) 56.dp else 38.dp,
                animationSpec = tween(durationMillis = 300),
                label = "segmentWidth"
            )

            StepSegment(
                modifier = Modifier.width(width),
                color = color
            )
        }
    }
}

@Composable
fun StepSegment(
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(10.dp)
            .padding(start = 15.dp)
            .clip(RoundedCornerShape(50))
            .background(color)
    )
}

@Preview
@Composable
fun StepIndicatorPreview() {
    AppTheme {
        StepIndicator(currentStep = PasswordResetStep.Code)
    }
}