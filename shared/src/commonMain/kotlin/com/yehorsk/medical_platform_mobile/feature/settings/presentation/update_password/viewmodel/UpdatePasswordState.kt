package com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel

import kotlinx.serialization.Serializable

data class UpdatePasswordState(
    val isLoading: Boolean = false,
    val isValid: Boolean = false,
    val form: UpdatePwdForm = UpdatePwdForm()
)

@Serializable
data class UpdatePwdForm(
    val currentPassword: String = "",
    val password: String = "",
    val passwordConfirm: String = ""
)