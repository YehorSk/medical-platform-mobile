package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme
import com.yehorsk.theme.extended

@Composable
fun MedConnectMessageBubble(
    message: String,
    senderName: String,
    timestamp: String,
    tailSide: MessageTail,
    modifier: Modifier = Modifier,
    bubbleColor: Color = MaterialTheme.colorScheme.extended.surfaceHigher,
    statusContent: (@Composable () -> Unit)? = null,
    tailSize: Dp = 16.dp,
    onLongPress: (() -> Unit)? = null
) {
    val horizontalPadding = 12.dp
    val verticalPadding = 12.dp

    val clickableModifier = if (onLongPress != null) {
        Modifier.combinedClickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(
                color = MaterialTheme.colorScheme.outline
            ),
            onClick = {},
            onLongClick = onLongPress
        )
    } else {
        Modifier
    }

    val startPadding = if (tailSide == MessageTail.START) {
        horizontalPadding + tailSize
    } else {
        horizontalPadding
    }

    val endPadding = if (tailSide == MessageTail.END) {
        horizontalPadding + tailSize
    } else {
        horizontalPadding
    }

    Column(
        modifier = modifier
            .then(clickableModifier)
            .clip(
                MedConnectMessageShape(
                    tail = tailSide,
                    tailSize = tailSize
                )
            )
            .background(bubbleColor)
            .padding(
                start = startPadding,
                end = endPadding,
                top = verticalPadding,
                bottom = verticalPadding
            )
            .width(IntrinsicSize.Max),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MessageHeader(
            sender = senderName,
            timestamp = timestamp
        )

        Text(
            text = message,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.extended.textPrimary
        )

        statusContent?.invoke()
    }
}

@Composable
private fun MessageHeader(
    sender: String,
    timestamp: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sender,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.extended.textSecondary
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = timestamp,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.extended.textSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MedConnectMessageBubbleIncomingPreview() {
    AppTheme {
        MedConnectMessageBubble(
            message = "Hi! I wanted to ask about my upcoming appointment.",
            senderName = "Dr. Smith",
            timestamp = "Today, 14:20",
            tailSide = MessageTail.START
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MedConnectMessageBubbleOutgoingPreview() {
    AppTheme {
        MedConnectMessageBubble(
            message = "Sure, your appointment is scheduled for tomorrow at 10:30.",
            senderName = "You",
            timestamp = "Today, 14:21",
            tailSide = MessageTail.END
        )
    }
}