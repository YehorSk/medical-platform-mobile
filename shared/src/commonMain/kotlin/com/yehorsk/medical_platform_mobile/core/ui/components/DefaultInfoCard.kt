package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.shield_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun DefaultInfoCard(
    modifier: Modifier = Modifier,
    iconRes: Painter? = null,
    title: String,
    content: String,
    secondContent: String? = null
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                iconRes?.let {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge
            )

            secondContent?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultInfoCardPreview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            DefaultInfoCard(
                iconRes = painterResource(UiRes.drawable.shield_24px),
                title = "Insurance",
                content = "Dovera",
                secondContent = "BCB=44821-JW"
            )

            DefaultInfoCard(
                title = "No Icon Example",
                content = "This card has no leading icon since iconRes is null."
            )
        }
    }
}