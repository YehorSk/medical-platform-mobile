package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Orbit: ImageVector
    get() {
        if (_Orbit != null) {
            return _Orbit!!
        }
        _Orbit = ImageVector.Builder(
            name = "Orbit",
            defaultWidth = 96.dp,
            defaultHeight = 20.dp,
            viewportWidth = 96f,
            viewportHeight = 20f
        ).apply {
            path(fill = SolidColor(Color(0xFFEEEEEE))) {
                moveTo(0f, 10f)
                arcToRelative(15f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = false, 30f, 0f)
                arcToRelative(15f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = false, -30f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFEEEEEE))) {
                moveTo(66f, 10f)
                arcToRelative(15f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = false, 30f, 0f)
                arcToRelative(15f, 10f, 0f, isMoreThanHalf = true, isPositiveArc = false, -30f, 0f)
                close()
            }
        }.build()

        return _Orbit!!
    }

@Suppress("ObjectPropertyName")
private var _Orbit: ImageVector? = null
