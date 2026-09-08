package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LeftShoulder: ImageVector
    get() {
        if (_LeftShoulder != null) {
            return _LeftShoulder!!
        }
        _LeftShoulder = ImageVector.Builder(
            name = "LeftShoulder",
            defaultWidth = 193.dp,
            defaultHeight = 210.dp,
            viewportWidth = 193f,
            viewportHeight = 210f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(87f, 65f)
                lineTo(185f, 141f)
                lineTo(193f, 210f)
                lineTo(163f, 173f)
                lineTo(113f, 155f)
                lineTo(53f, 101f)
                lineTo(87f, 65f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(111f, 48f)
                lineTo(179f, 106f)
                lineTo(191f, 141f)
                lineTo(93f, 57f)
                lineTo(111f, 48f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(11f, 0f)
                lineTo(95f, 50f)
                lineTo(63f, 64f)
                lineTo(0f, 50f)
                lineTo(11f, 0f)
                close()
            }
        }.build()

        return _LeftShoulder!!
    }

@Suppress("ObjectPropertyName")
private var _LeftShoulder: ImageVector? = null
