package com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel

import com.yehorsk.medical_platform_mobile.core.domain.model.User
import kotlinx.serialization.Serializable

data class SettingsState(
    val user: User ?= null,
    val isLoading: Boolean = false,
    val form: ProfileForm = ProfileForm(),
    val showLogoutConfirmation: Boolean = false
)

@Serializable
data class ProfileForm(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val title: String = "",
    val phone: String = "",
    val address: String = "",
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
)
