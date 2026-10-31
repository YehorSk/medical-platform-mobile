package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Participant
import com.yehorsk.medical_platform_mobile.util.UiText

enum class MessageDeliveryStatus {
    SENT,
    SENDING,
    FAILED
}

sealed class MessageUi(open val id: String){
    data class LocalUserMessage(
        override val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val deliveryStatus: MessageDeliveryStatus = MessageDeliveryStatus.SENT,
        // Set for messages the server hasn't confirmed yet, used to retry / discard them
        val localId: String? = null,
        val error: UiText? = null
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
