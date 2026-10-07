package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import com.yehorsk.theme.extended
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.you
import org.jetbrains.compose.resources.stringResource

@Composable
fun LocalUserMessage(
    message: MessageUi.LocalUserMessage,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
    ) {
        Box {
            MedConnectMessageBubble(
                message = message.content,
                senderName = stringResource(UiRes.string.you),
                timestamp = message.formattedSentTime.asString(),
                tailSide = MessageTail.END,
                statusContent = {
//                    MessageStatus(
//                        status = message.deliveryStatus
//                    )
                }
            )
        }

//        if(message.deliveryStatus == ChatMessageDeliveryStatus.FAILED) {
//            IconButton(
//                onClick = onRetryClick
//            ) {
//                Icon(
//                    imageVector = vectorResource(Res.drawable.reload_icon),
//                    contentDescription = stringResource(Res.string.retry),
//                    tint = MaterialTheme.colorScheme.error
//                )
//            }
//        }
    }
}