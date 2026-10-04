package com.yehorsk.medical_platform_mobile.feature.chat.data.mappers

import com.yehorsk.medical_platform_mobile.feature.chat.data.dto.ConversationDto
import com.yehorsk.medical_platform_mobile.feature.chat.data.dto.MessageDto
import com.yehorsk.medical_platform_mobile.feature.chat.data.dto.ParticipantDto
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Participant
import kotlin.time.Instant

fun ConversationDto.toDomain(): Conversation =
    Conversation(
        id = id,
        patient = patient.toDomain(),
        doctor = doctor.toDomain(),
        lastMessageAt = lastMessageAt?.let(Instant::parse),
        lastMessage = lastMessage.toDomain()
    )

fun ParticipantDto.toDomain(): Participant =
    Participant(
        userId = userId,
        firstName = firstName,
        lastName = lastName,
        email = email,
        title = title
    )

fun MessageDto.toDomain(): Message =
    Message(
        id = id,
        conversationId = conversationId,
        senderId = senderId,
        content = content,
        createdAt = Instant.parse(createdAt)
    )