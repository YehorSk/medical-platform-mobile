package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LeftFoot: ImageVector
    get() {
        if (_LeftFoot != null) {
            return _LeftFoot!!
        }
        _LeftFoot = ImageVector.Builder(
            name = "LeftFoot",
            defaultWidth = 86.dp,
            defaultHeight = 90.dp,
            viewportWidth = 86f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(18f, 0f)
                lineTo(0f, 30f)
                lineTo(6f, 90f)
                horizontalLineTo(18f)
                lineTo(22f, 78f)
                lineTo(27f, 90f)
                lineTo(81f, 88f)
                lineTo(86f, 69f)
                lineTo(64f, 22f)
                lineTo(18f, 0f)
                close()
            }
        }.build()

        return _LeftFoot!!
    }

@Suppress("ObjectPropertyName")
private var _LeftFoot: ImageVector? = null
