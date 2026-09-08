package com.yehorsk.medical_platform_mobile.util.body.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val LeftArm: ImageVector
    get() {
        if (_LeftArm != null) {
            return _LeftArm!!
        }
        _LeftArm = ImageVector.Builder(
            name = "LeftArm",
            defaultWidth = 206.dp,
            defaultHeight = 438.dp,
            viewportWidth = 206f,
            viewportHeight = 438f
        ).apply {
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(30f, 0f)
                lineTo(0f, 42f)
                lineTo(40f, 167f)
                lineTo(119f, 200f)
                verticalLineTo(181f)
                lineTo(63f, 48f)
                lineTo(30f, 0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(78f, 35f)
                lineTo(126f, 173f)
                lineTo(113f, 63f)
                lineTo(78f, 35f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(41f, 178f)
                lineTo(50f, 206f)
                lineTo(116f, 245f)
                verticalLineTo(215f)
                lineTo(41f, 178f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(122f, 206f)
                verticalLineTo(261f)
                lineTo(190f, 422f)
                lineTo(206f, 414f)
                lineTo(176f, 298f)
                lineTo(122f, 206f)
                close()
            }
            path(fill = SolidColor(Color(0xFFD9D9D9))) {
                moveTo(54f, 225f)
                lineTo(68f, 286f)
                lineTo(154f, 438f)
                lineTo(180f, 426f)
                lineTo(104f, 250f)
                lineTo(54f, 225f)
                close()
            }
        }.build()

        return _LeftArm!!
    }

@Suppress("ObjectPropertyName")
private var _LeftArm: ImageVector? = null
