package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RightShoulder: ImageVector
    get() {
        if (_RightShoulder != null) {
            return _RightShoulder!!
        }
        _RightShoulder = ImageVector.Builder(
            name = "RightShoulder",
            defaultWidth = 154.dp,
            defaultHeight = 218.dp,
            viewportWidth = 154f,
            viewportHeight = 218f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(135f, 0f)
                lineTo(154f, 52f)
                horizontalLineTo(105f)
                lineTo(73f, 36f)
                lineTo(135f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(35f, 51f)
                horizontalLineTo(53.92f)
                lineTo(0f, 151.21f)
                verticalLineTo(107f)
                lineTo(35f, 51f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(58.92f, 55.21f)
                horizontalLineTo(99.92f)
                lineTo(74.92f, 142.21f)
                lineTo(35.92f, 165.21f)
                lineTo(4.92f, 217.21f)
                verticalLineTo(155.21f)
                lineTo(58.92f, 55.21f)
                close()
            }
        }.build()

        return _RightShoulder!!
    }

@Suppress("ObjectPropertyName")
private var _RightShoulder: ImageVector? = null
