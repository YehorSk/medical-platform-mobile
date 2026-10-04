package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.mappers

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import com.yehorsk.medical_platform_mobile.util.DateUtils
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun List<Message>.toUiList(userId: String): List<MessageUi>{
    return this
        .sortedByDescending { it.createdAt }
        .groupBy {
            it.createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date
        }
        .flatMap { (date, messages) ->
            messages.map { it.toUi(userId) } + MessageUi.DateSeparator(
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