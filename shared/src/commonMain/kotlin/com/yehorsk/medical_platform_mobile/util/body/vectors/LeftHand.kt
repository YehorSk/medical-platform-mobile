package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LeftHand: ImageVector
    get() {
        if (_LeftHand != null) {
            return _LeftHand!!
        }
        _LeftHand = ImageVector.Builder(
            name = "LeftHand",
            defaultWidth = 127.dp,
            defaultHeight = 170.dp,
            viewportWidth = 127f,
            viewportHeight = 170f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(56.27f, 0f)
                lineTo(41.27f, 27f)
                lineTo(2.27f, 36f)
                curveTo(2.27f, 36f, 1.85f, 62.77f, 0.27f, 83f)
                curveTo(-2.36f, 116.71f, 15.27f, 170f, 15.27f, 170f)
                horizontalLineTo(26.27f)
                lineTo(19.27f, 131f)
                lineTo(30.27f, 126f)
                lineTo(43.27f, 167f)
                lineTo(56.27f, 165f)
                lineTo(45.27f, 119f)
                lineTo(54.27f, 113f)
                lineTo(71.27f, 158f)
                lineTo(85.27f, 157f)
                lineTo(68.27f, 106f)
                lineTo(77.27f, 101f)
                lineTo(99.27f, 151f)
                lineTo(109.27f, 149f)
                lineTo(85.27f, 66f)
                lineTo(89.27f, 53f)
                lineTo(120.27f, 73f)
                lineTo(126.27f, 66f)
                lineTo(99.27f, 27f)
                lineTo(56.27f, 0f)
                close()
            }
        }.build()

        return _LeftHand!!
    }

@Suppress("ObjectPropertyName")
private var _LeftHand: ImageVector? = null
