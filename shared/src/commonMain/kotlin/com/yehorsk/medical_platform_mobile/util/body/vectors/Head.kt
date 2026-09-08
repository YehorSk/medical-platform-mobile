package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Head: ImageVector
    get() {
        if (_Head != null) {
            return _Head!!
        }
        _Head = ImageVector.Builder(
            name = "Head",
            defaultWidth = 181.dp,
            defaultHeight = 250.dp,
            viewportWidth = 181f,
            viewportHeight = 250f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(12.68f, 53f)
                lineTo(11.66f, 110.64f)
                lineTo(0f, 109.49f)
                lineTo(3f, 142f)
                lineTo(18.66f, 148f)
                lineTo(30.68f, 198f)
                lineTo(62.68f, 250f)
                horizontalLineTo(120.68f)
                lineTo(156.68f, 198f)
                lineTo(164.68f, 143f)
                lineTo(178.68f, 132f)
                lineTo(180.68f, 103f)
                lineTo(169.68f, 104f)
                lineTo(168.68f, 49.08f)
                curveTo(168.68f, 49.08f, 125.11f, -0.23f, 88.68f, 1f)
                curveTo(52.73f, 2.21f, 12.68f, 53f, 12.68f, 53f)
                close()
            }
        }.build()

        return _Head!!
    }

@Suppress("ObjectPropertyName")
private var _Head: ImageVector? = null
