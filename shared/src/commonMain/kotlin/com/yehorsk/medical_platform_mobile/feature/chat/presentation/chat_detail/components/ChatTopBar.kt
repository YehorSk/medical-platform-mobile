package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Participant
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_back_24px
import medicalplatformmobile.shared.generated.resources.chat_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/** Top bar of an open chat: the other participant's initials, name and a status line. */
@Composable
fun ChatTopBar(
    participant: Participant?,
    subtitle: String?,
    onGoBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    showGoBackButton: Boolean = true,
    onParticipantClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (showGoBackButton) {
            IconButton(onClick = onGoBackClicked) {
                Icon(
                    painter = painterResource(UiRes.drawable.arrow_back_24px),
                    contentDescription = stringResource(UiRes.string.chat_back),
                    tint = Color.White
                )
            }
        }

        if (participant != null) Row(
            modifier = Modifier
                .weight(1f)
                .clip(MaterialTheme.shapes.small)
                .clickable(onClick = onParticipantClick)
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = participant.initials(),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = participant.displayName(),
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

internal fun Participant.displayName(): String =
    listOfNotNull(title?.takeIf { it.isNotBlank() }, firstName, lastName).joinToString(" ")

internal fun Participant.initials(): String =
    "${firstName.firstOrNull() ?: ""}${lastName.firstOrNull() ?: ""}".uppercase()

@Preview
@Composable
private fun ChatTopBarPreview() {
    AppTheme {
        ChatTopBar(
            participant = Participant(
                userId = "1",
                firstName = "John",
                lastName = "Smith",
                email = "john@example.com",
                title = "MUDr."
            ),
            subtitle = "Doctor",
            onGoBackClicked = {}
        )
    }
}
