package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RightArm: ImageVector
    get() {
        if (_RightArm != null) {
            return _RightArm!!
        }
        _RightArm = ImageVector.Builder(
            name = "RightArm",
            defaultWidth = 223.dp,
            defaultHeight = 445.dp,
            viewportWidth = 223f,
            viewportHeight = 445f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(193f, 0f)
                lineTo(223f, 42f)
                lineTo(183f, 167f)
                lineTo(104f, 200f)
                verticalLineTo(181f)
                lineTo(160f, 48f)
                lineTo(193f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(144f, 14f)
                lineTo(98f, 191f)
                lineTo(110f, 69f)
                lineTo(144f, 14f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(181f, 175f)
                lineTo(172f, 203f)
                lineTo(106f, 242f)
                verticalLineTo(212f)
                lineTo(181f, 175f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(90f, 210f)
                lineTo(100f, 256f)
                lineTo(18f, 432f)
                lineTo(0f, 422f)
                lineTo(36f, 302f)
                lineTo(90f, 210f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(163f, 234f)
                lineTo(149f, 295f)
                lineTo(48f, 445f)
                lineTo(25f, 439f)
                lineTo(114f, 253f)
                lineTo(163f, 234f)
                close()
            }
        }.build()

        return _RightArm!!
    }

@Suppress("ObjectPropertyName")
private var _RightArm: ImageVector? = null
