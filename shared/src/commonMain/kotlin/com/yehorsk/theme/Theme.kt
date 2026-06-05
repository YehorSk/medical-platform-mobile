package com.yehorsk.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.yehorsk.theme.statusPendingLight

val LocalExtendedColors = staticCompositionLocalOf {
    LightExtendedColors
}

val ColorScheme.extended: ExtendedColors
    @ReadOnlyComposable
    @Composable
    get() = LocalExtendedColors.current

@Immutable
data class ExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textDisabled: Color,
    val surfaceHigher: Color,
    val surfaceLower: Color,
    val accentBlue: Color,

    // Status colors
    val statusPending: Color,
    val statusConfirmed: Color,
    val statusRejected: Color,
    val statusCancelled: Color,
    val statusCompleted: Color,

)

val LightExtendedColors = ExtendedColors(
    success = Color(0xFF4CAF50),
    onSuccess = Color.White,

    textPrimary = onSurfaceLight,
    textSecondary = onSurfaceVariantLight,
    textDisabled = outlineLight,

    surfaceHigher = surfaceContainerHighLight,
    surfaceLower = surfaceContainerLowLight,

    accentBlue = primaryLight,
    statusPending = statusPendingLight,
    statusConfirmed = statusConfirmedLight,
    statusRejected = statusRejectedLight,
    statusCancelled = statusCancelledLight,
    statusCompleted = statusCompletedLight
)

val DarkExtendedColors = ExtendedColors(
    success = Color(0xFF81C784),
    onSuccess = Color.Black,

    textPrimary = onSurfaceDark,
    textSecondary = onSurfaceVariantDark,
    textDisabled = outlineDark,

    surfaceHigher = surfaceContainerHighDark,
    surfaceLower = surfaceContainerLowDark,

    accentBlue = primaryDark,
    statusPending = statusPendingDark,
    statusConfirmed = statusConfirmedDark,
    statusRejected = statusRejectedDark,
    statusCancelled = statusCancelledLight,
    statusCompleted = statusCompletedDark
)

val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)
