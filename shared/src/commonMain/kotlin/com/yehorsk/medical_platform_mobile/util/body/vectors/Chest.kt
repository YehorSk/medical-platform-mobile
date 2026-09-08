package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Chest: ImageVector
    get() {
        if (_Chest != null) {
            return _Chest!!
        }
        _Chest = ImageVector.Builder(
            name = "Chest",
            defaultWidth = 289.dp,
            defaultHeight = 165.dp,
            viewportWidth = 289f,
            viewportHeight = 165f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(53f, 0f)
                lineTo(114f, 13f)
                lineTo(132f, 121f)
                lineTo(71f, 165f)
                lineTo(23f, 148f)
                verticalLineTo(97f)
                lineTo(0f, 70f)
                lineTo(35f, 55f)
                lineTo(53f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(221f, 0f)
                lineTo(252f, 50.21f)
                horizontalLineTo(289f)
                lineTo(277f, 76f)
                verticalLineTo(128f)
                lineTo(227f, 162f)
                lineTo(160.41f, 116f)
                lineTo(166.41f, 10.21f)
                lineTo(221f, 0f)
                close()
            }
        }.build()

        return _Chest!!
    }

@Suppress("ObjectPropertyName")
private var _Chest: ImageVector? = null
