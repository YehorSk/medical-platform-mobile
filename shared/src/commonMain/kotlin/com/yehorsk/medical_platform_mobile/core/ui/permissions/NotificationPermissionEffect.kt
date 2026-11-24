package com.yehorsk.medical_platform_mobile.core.ui.permissions

import androidx.compose.runtime.Composable

// Asks for permission to show notifications once the user is signed in (Android 13+ only).
@Composable
expect fun NotificationPermissionEffect(isLoggedIn: Boolean)
