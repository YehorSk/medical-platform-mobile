package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageDeliveryStatus
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat_discard
import medicalplatformmobile.shared.generated.resources.chat_retry
import medicalplatformmobile.shared.generated.resources.chat_sending
import medicalplatformmobile.shared.generated.resources.you
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocalUserMessage(
    message: MessageUi.LocalUserMessage,
    onRetryClick: () -> Unit,
    onDiscardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
    ) {
        if(message.deliveryStatus == MessageDeliveryStatus.FAILED) {
            IconButton(
                onClick = onDiscardClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(UiRes.string.chat_discard),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(
                onClick = onRetryClick
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = stringResource(UiRes.string.chat_retry),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Box {
                MedConnectMessageBubble(
                    message = message.content,
                    senderName = stringResource(UiRes.string.you),
                    timestamp = message.formattedSentTime.asString(),
                    tailSide = MessageTail.END,
                    statusContent = if (message.deliveryStatus == MessageDeliveryStatus.SENDING) {
                        {
                            Text(
                                text = stringResource(UiRes.string.chat_sending),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else null
                )
            }
            if (message.deliveryStatus == MessageDeliveryStatus.FAILED && message.error != null) {
                Text(
                    text = message.error.asString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
