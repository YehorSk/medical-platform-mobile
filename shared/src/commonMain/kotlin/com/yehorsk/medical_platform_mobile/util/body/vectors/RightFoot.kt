package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RightFoot: ImageVector
    get() {
        if (_RightFoot != null) {
            return _RightFoot!!
        }
        _RightFoot = ImageVector.Builder(
            name = "RightFoot",
            defaultWidth = 86.dp,
            defaultHeight = 90.dp,
            viewportWidth = 86f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(68f, 0f)
                lineTo(86f, 30f)
                lineTo(80f, 90f)
                horizontalLineTo(68f)
                lineTo(64f, 78f)
                lineTo(59f, 90f)
                lineTo(5f, 88f)
                lineTo(0f, 69f)
                lineTo(22f, 22f)
                lineTo(68f, 0f)
                close()
            }
        }.build()

        return _RightFoot!!
    }

@Suppress("ObjectPropertyName")
private var _RightFoot: ImageVector? = null
