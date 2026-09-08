package com.yehorsk.medical_platform_mobile.feature.auth.presentation.local_auth.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.util.UiText

data class LocalAuthState(
    val isLoading: Boolean = false,
    val userId: String? = null,
    val userRole: UserRole? = null,
    val pinCode: String = "",
    val pinCodeConfirm: String = "",
    val error: UiText? = null,
    val isFormValid: Boolean = false,
    val localAuthType: LocalAuthType = LocalAuthType.CREATE_PIN
)

enum class LocalAuthType {
    CREATE_PIN, CONFIRM_PIN, ENTER_PIN
}