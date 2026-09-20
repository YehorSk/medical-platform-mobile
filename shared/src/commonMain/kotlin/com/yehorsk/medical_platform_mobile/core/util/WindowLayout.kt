package com.yehorsk.medical_platform_mobile.core.util

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

@Composable
fun currentWindowLayout(): WindowLayout =
    WindowLayout.of(currentWindowAdaptiveInfo().windowSizeClass)

enum class WindowLayout {
    MOBILE_PORTRAIT, MOBILE_LANDSCAPE, TABLET_PORTRAIT, TABLET_LANDSCAPE, DESKTOP;

    val isCompact: Boolean
        get() = this == MOBILE_PORTRAIT || this == MOBILE_LANDSCAPE

    val isExpanded: Boolean
        get() = this == TABLET_LANDSCAPE || this == DESKTOP

    companion object {
        fun of(windowSizeClass: WindowSizeClass): WindowLayout = with(windowSizeClass) {
            when {
                minHeightDp < HEIGHT_DP_MEDIUM_LOWER_BOUND -> MOBILE_LANDSCAPE
                minWidthDp < WIDTH_DP_MEDIUM_LOWER_BOUND -> MOBILE_PORTRAIT
                minWidthDp < WIDTH_DP_EXPANDED_LOWER_BOUND -> TABLET_PORTRAIT
                minHeightDp < HEIGHT_DP_EXPANDED_LOWER_BOUND -> TABLET_LANDSCAPE
                else -> DESKTOP
            }
        }
    }
}