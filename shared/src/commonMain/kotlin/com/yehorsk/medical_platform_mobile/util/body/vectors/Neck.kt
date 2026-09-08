package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Neck: ImageVector
    get() {
        if (_Neck != null) {
            return _Neck!!
        }
        _Neck = ImageVector.Builder(
            name = "Neck",
            defaultWidth = 149.dp,
            defaultHeight = 126.dp,
            viewportWidth = 149f,
            viewportHeight = 126f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(10.27f, 0f)
                lineTo(52.3f, 53.9f)
                lineTo(76f, 126f)
                lineTo(28.76f, 111.3f)
                lineTo(0f, 27.3f)
                lineTo(10.27f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(142f, 0f)
                lineTo(149f, 16f)
                lineTo(126f, 98f)
                lineTo(82f, 126f)
                lineTo(96f, 56f)
                lineTo(142f, 0f)
                close()
            }
        }.build()

        return _Neck!!
    }

@Suppress("ObjectPropertyName")
private var _Neck: ImageVector? = null
