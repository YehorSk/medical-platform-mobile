package com.yehorsk.medical_platform_mobile.core.ui

import com.yehorsk.medical_platform_mobile.core.domain.model.User

data class AppState(
    val user: User? = null,
    val notificationCount: Int = 0
)
