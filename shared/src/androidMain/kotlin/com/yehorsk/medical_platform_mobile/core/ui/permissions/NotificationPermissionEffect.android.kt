package com.yehorsk.medical_platform_mobile.core.ui.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
actual fun NotificationPermissionEffect(isLoggedIn: Boolean) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    val context = LocalContext.current
    // Ask at most once per activity lifetime; after two denials Android stops showing the dialog anyway.
    var asked by rememberSaveable { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn || asked) return@LaunchedEffect
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) {
            asked = true
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
