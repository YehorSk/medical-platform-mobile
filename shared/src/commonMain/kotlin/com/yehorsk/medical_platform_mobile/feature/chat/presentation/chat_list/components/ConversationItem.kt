package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.feature.auth.domain.models.AuthData
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.util.formatTimeAgo
import com.yehorsk.medical_platform_mobile.util.getRole
import com.yehorsk.medical_platform_mobile.util.toText

@Composable
fun ConversationItem(
    modifier: Modifier = Modifier,
    conversation: Conversation,
    localUser: AuthData,
    onClick: () -> Unit
) {
    val lastMessage = conversation.lastMessage

    val participant = when (getRole(localUser.user.role)) {
        UserRole.PATIENT -> conversation.doctor
        UserRole.DOCTOR -> conversation.patient
    }

    val participantName = "${participant.title} ${participant.firstName} ${participant.lastName}"
    val participantInitial = participant.firstName
        .firstOrNull()
        ?.uppercaseChar()
        ?.toString()
        ?: "?"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = participantInitial,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

//            if (!lastMessage.isRead) {
//                Box(
//                    modifier = Modifier
//                        .size(10.dp)
//                        .background(
//                            color = MaterialTheme.colorScheme.primary,
//                            shape = CircleShape
//                        )
//                        .border(
//                            width = 2.dp,
//                            color = MaterialTheme.colorScheme.surface,
//                            shape = CircleShape
//                        )
//                )
//            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = participantName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = lastMessage?.content ?: "---",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        lastMessage?.let {
            Text(
                text = formatTimeAgo(it.createdAt.toString()).toText(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}