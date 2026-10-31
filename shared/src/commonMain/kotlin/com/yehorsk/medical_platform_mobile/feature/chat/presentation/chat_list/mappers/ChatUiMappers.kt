package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.mappers

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.ChatSendError
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.PendingMessage
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.PendingMessageStatus
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageDeliveryStatus
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import com.yehorsk.medical_platform_mobile.util.DateUtils
import com.yehorsk.medical_platform_mobile.util.UiText
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat_send_error_forbidden
import medicalplatformmobile.shared.generated.resources.chat_send_error_invalid_content
import medicalplatformmobile.shared.generated.resources.chat_send_error_not_active
import medicalplatformmobile.shared.generated.resources.chat_send_error_server

fun List<Message>.toUiList(
    userId: String,
    pending: List<PendingMessage> = emptyList()
): List<MessageUi>{
    // Unconfirmed messages always sit at the bottom (newest), even if the server clock runs ahead
    val pendingEntries = pending
        .sortedByDescending { it.createdAt }
        .map { it.createdAt to it.toUi() }
    val sentEntries = this
        .sortedByDescending { it.createdAt }
        .map { it.createdAt to it.toUi(userId) }

    return (pendingEntries + sentEntries)
        .groupBy { (createdAt, _) ->
            createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date
        }
        .flatMap { (date, entries) ->
            entries.map { it.second } + MessageUi.DateSeparator(
                id = date.toString(),
                date = DateUtils.formatDateSeparator(date)
            )
        }
}

fun Message.toUi(userId: String): MessageUi{
    return if(userId == this.sender.userId) {
        MessageUi.LocalUserMessage(
            id = id,
            content = content,
            formattedSentTime = DateUtils.formatMessageTime(instant = createdAt)
        )
    } else {
        MessageUi.OtherUserMessage(
            id = id,
            content = content,
            formattedSentTime = DateUtils.formatMessageTime(instant = createdAt),
            sender = sender
        )
    }
}

fun PendingMessage.toUi(): MessageUi.LocalUserMessage {
    return MessageUi.LocalUserMessage(
        id = "pending-$localId",
        content = content,
        formattedSentTime = DateUtils.formatMessageTime(instant = createdAt),
        deliveryStatus = when (status) {
            PendingMessageStatus.SENDING -> MessageDeliveryStatus.SENDING
            PendingMessageStatus.FAILED -> MessageDeliveryStatus.FAILED
        },
        localId = localId,
        error = error?.toUiText()
    )
}

fun ChatSendError.toUiText(): UiText = UiText.Resource(
    when (this) {
        ChatSendError.CONVERSATION_NOT_ACTIVE -> UiRes.string.chat_send_error_not_active
        ChatSendError.FORBIDDEN -> UiRes.string.chat_send_error_forbidden
        ChatSendError.INVALID_CONTENT -> UiRes.string.chat_send_error_invalid_content
        ChatSendError.SERVER_ERROR -> UiRes.string.chat_send_error_server
    }
)
