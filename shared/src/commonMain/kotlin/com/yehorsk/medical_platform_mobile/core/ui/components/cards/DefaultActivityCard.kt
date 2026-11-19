package com.yehorsk.medical_platform_mobile.core.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.other.DefaultStatusBadge
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.shield_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun DefaultActivityCard(
    icon: Painter,
    title: String,
    modifier: Modifier = Modifier,
    details: List<String> = emptyList(),
    timestamp: String? = null,
    highlighted: Boolean = false,
    iconTint: Color = if (highlighted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
    onClick: (() -> Unit)? = null,
    badges: @Composable RowScope.() -> Unit = {}
) {
    val colors = CardDefaults.cardColors(
        containerColor = if (highlighted) MaterialTheme.colorScheme.errorContainer
        else MaterialTheme.colorScheme.surface
    )
    val elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    val shape = RoundedCornerShape(18.dp)

    val content: @Composable () -> Unit = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (highlighted) MaterialTheme.colorScheme.onErrorContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                details.forEach { line ->
                    Text(
                        text = line,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    timestamp?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    badges()
                }
            }
        }
    }

    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = colors,
            elevation = elevation
        ) { content() }
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = colors,
            elevation = elevation
        ) { content() }
    }
}

@Preview
@Composable
private fun DefaultActivityCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DefaultActivityCard(
                icon = painterResource(UiRes.drawable.shield_24px),
                title = "Failed sign-in: wrong password",
                details = listOf("192.168.0.12", "Chrome on Windows"),
                timestamp = "17.11.2026, 10:41",
                highlighted = true,
                badges = {
                    DefaultStatusBadge(
                        text = "Failed",
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                }
            )
            DefaultActivityCard(
                icon = painterResource(UiRes.drawable.shield_24px),
                title = "Signed in",
                details = listOf("Medical Platform app"),
                timestamp = "17.11.2026, 09:02",
                onClick = {}
            )
        }
    }
}
