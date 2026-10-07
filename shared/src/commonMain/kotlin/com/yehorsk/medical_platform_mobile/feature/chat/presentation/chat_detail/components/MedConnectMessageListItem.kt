package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi

@Composable
fun MedConnectMessageListItem(
    messageUi: MessageUi,
    onRetryClick: (MessageUi.LocalUserMessage) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        when (messageUi) {
            is MessageUi.DateSeparator -> {
                MedConnectDateDivider(
                    date = messageUi.date.asString(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is MessageUi.LocalUserMessage -> {
                LocalUserMessage(
                    message = messageUi,
                    onRetryClick = {
                        onRetryClick(messageUi)
                    }
                )
            }

            is MessageUi.OtherUserMessage -> {
                OtherUserMessage(
                    message = messageUi,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun MedConnectLocalMessagePreview() {
//    MedConnectTheme {
//        MedConnectMessageListItem(
//            messageUi = MessageUi.LocalUserMessage(
//                id = "1",
//                content = "Hello world, this is a preview message that spans multiple lines",
//                deliveryStatus = ChatMessageDeliveryStatus.SENT,
//                formattedSentTime = UiText.DynamicString("Friday 2:20pm")
//            ),
//            messageWithOpenMenu = null,
//            onRetryClick = {},
//            onMessageLongClick = {},
//            onDismissMessageMenu = {},
//            onDeleteClick = {},
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MedConnectFailedMessagePreview() {
//    MedConnectTheme {
//        MedConnectMessageListItem(
//            messageUi = MessageUi.LocalUserMessage(
//                id = "1",
//                content = "Hello world, this is a preview message that spans multiple lines",
//                deliveryStatus = ChatMessageDeliveryStatus.FAILED,
//                formattedSentTime = UiText.DynamicString("Friday 2:20pm")
//            ),
//            messageWithOpenMenu = null,
//            onRetryClick = {},
//            onMessageLongClick = {},
//            onDismissMessageMenu = {},
//            onDeleteClick = {},
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//private fun MedConnectOtherMessagePreview() {
//    MedConnectTheme {
//        MedConnectMessageListItem(
//            messageUi = MessageUi.OtherUserMessage(
//                id = "1",
//                content = "Hello world, this is a preview message that spans multiple lines",
//                formattedSentTime = UiText.DynamicString("Friday 2:20pm"),
//                sender = ChatParticipantUi(
//                    id = "1",
//                    username = "Philipp",
//                    initials = "PH"
//                )
//            ),
//            messageWithOpenMenu = null,
//            onRetryClick = {},
//            onMessageLongClick = {},
//            onDismissMessageMenu = {},
//            onDeleteClick = {},
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}