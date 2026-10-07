package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi

@Composable
fun OtherUserMessage(
    message: MessageUi.OtherUserMessage,
    color: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
//        ChirpAvatarPhoto(
//            displayText = message.sender.initials,
//            imageUrl = message.sender.imageUrl
//        )
        MedConnectMessageBubble(
            message = message.content,
            senderName = message.sender.firstName,
            tailSide = MessageTail.START,
            bubbleColor = color,
            timestamp = message.formattedSentTime.asString()
        )
    }
}