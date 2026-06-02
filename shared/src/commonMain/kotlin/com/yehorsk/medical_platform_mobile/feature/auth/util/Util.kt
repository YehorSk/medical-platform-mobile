package com.yehorsk.medical_platform_mobile.feature.auth.util

import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole

fun getRole(role: String): UserRole {
    return UserRole.entries.find { it.name.equals(role, ignoreCase = true) } ?: UserRole.PATIENT
}