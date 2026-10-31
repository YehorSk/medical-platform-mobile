package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Participant
import com.yehorsk.medical_platform_mobile.util.formatTimeAgo
import com.yehorsk.medical_platform_mobile.util.toText
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat_you_prefix
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock

/** Conversation card for the home screen's "Recent messages" block. */
@Composable
fun DashChatItem(
    modifier: Modifier = Modifier,
    conversation: Conversation,
    localUserId: String,
    onClick: () -> Unit
) {
    val lastMessage = conversation.lastMessage
    // The card is about the other person, even when the last message is our own
    val participant = if (localUserId == conversation.patient.userId) conversation.doctor else conversation.patient
    val participantName = listOfNotNull(
        participant.title?.takeIf { it.isNotBlank() },
        participant.firstName,
        participant.lastName
    ).joinToString(" ")
    val preview = when {
        lastMessage == null -> null
        lastMessage.sender.userId == localUserId -> stringResource(UiRes.string.chat_you_prefix) + lastMessage.content
        else -> lastMessage.content
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = participant.firstName.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = participantName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (preview != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = preview,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (lastMessage != null) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = formatTimeAgo(lastMessage.createdAt.toString()).toText(),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
private fun DashChatItemPreview() {
    val doctor = Participant(
        userId = "2",
        firstName = "Sarah",
        lastName = "Johnson",
        email = "sarah@example.com",
        title = "Dr."
    )
    AppTheme {
        DashChatItem(
            conversation = Conversation(
                id = "1",
                patient = Participant(
                    userId = "1",
                    firstName = "John",
                    lastName = "Doe",
                    email = "john@example.com"
                ),
                doctor = doctor,
                lastMessage = Message(
                    id = "1",
                    conversationId = "1",
                    sender = doctor,
                    content = "Your test results are ready",
                    createdAt = Clock.System.now()
                )
            ),
            localUserId = "1",
            onClick = {}
        )
    }
}
