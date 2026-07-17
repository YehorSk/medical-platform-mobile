package com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel

import kotlinx.serialization.Serializable
import javax.management.relation.Role

data class LoginState(
    val loginForm: LoginForm = LoginForm(),
    val isEntryValid: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val passwordVisible: Boolean = false,
    val isAuthenticating: Boolean = true,
    val error: String = "",
    val role: Role? = null,
)

@Serializable
data class LoginForm(
    val email: String = "john.doe@example.com",
    val password: String = "12345678",
)
