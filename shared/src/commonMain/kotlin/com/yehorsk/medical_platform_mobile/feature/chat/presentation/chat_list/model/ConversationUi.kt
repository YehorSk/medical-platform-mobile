package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model

import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Participant

data class ConversationUi(
    val id: String,
    val patient: Participant,
    val doctor: Participant,
    val lastMessageAt: String? = null,
)
