package com.yehorsk.medical_platform_mobile.feature.auth.presentation.email_verification.viewmodel

data class EmailVerificationState(
    val isVerified: Boolean = false,
    val isVerifying: Boolean = false
)