package com.yehorsk.medical_platform_mobile.feature.chat.domain.model

data class InAppNotification(
    val title: String,
    val body: String,
    val data: Map<String, String>
)
