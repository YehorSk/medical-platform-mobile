package com.yehorsk.medical_platform_mobile.feature.auth.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.util.WindowLayout

private val AuthFormMaxWidth = 480.dp

fun Modifier.authFormWidth(windowLayout: WindowLayout): Modifier =
    if (windowLayout.isCompact) {
        fillMaxWidth()
    } else {
        widthIn(max = AuthFormMaxWidth).fillMaxWidth()
    }
