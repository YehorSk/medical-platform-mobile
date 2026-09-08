package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RightHand: ImageVector
    get() {
        if (_RightHand != null) {
            return _RightHand!!
        }
        _RightHand = ImageVector.Builder(
            name = "RightHand",
            defaultWidth = 127.dp,
            defaultHeight = 170.dp,
            viewportWidth = 127f,
            viewportHeight = 170f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(70f, 0f)
                lineTo(85f, 27f)
                lineTo(123f, 32f)
                curveTo(123f, 32f, 124.42f, 62.77f, 126f, 83f)
                curveTo(128.63f, 116.71f, 111f, 170f, 111f, 170f)
                horizontalLineTo(100f)
                lineTo(107f, 131f)
                lineTo(96f, 126f)
                lineTo(83f, 167f)
                lineTo(70f, 165f)
                lineTo(81f, 119f)
                lineTo(72f, 113f)
                lineTo(55f, 158f)
                lineTo(41f, 157f)
                lineTo(58f, 106f)
                lineTo(49f, 101f)
                lineTo(27f, 151f)
                lineTo(17f, 149f)
                lineTo(41f, 66f)
                lineTo(37f, 53f)
                lineTo(6f, 73f)
                lineTo(0f, 66f)
                lineTo(27f, 27f)
                lineTo(70f, 0f)
                close()
            }
        }.build()

        return _RightHand!!
    }

@Suppress("ObjectPropertyName")
private var _RightHand: ImageVector? = null
