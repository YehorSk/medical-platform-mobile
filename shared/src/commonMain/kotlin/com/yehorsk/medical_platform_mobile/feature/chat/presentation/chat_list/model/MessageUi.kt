package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Participant
import com.yehorsk.medical_platform_mobile.util.UiText

sealed class MessageUi(open val id: String){
    data class LocalUserMessage(
        override val id: String,
        val content: String,
        val formattedSentTime: UiText
    ): MessageUi(id)

    data class OtherUserMessage(
        override val id: String,
        val content: String,
        val sender: Participant,
        val formattedSentTime: UiText
    ): MessageUi(id)

    data class DateSeparator(
        override val id: String,
        val date: UiText,
    ): MessageUi(id)
}
