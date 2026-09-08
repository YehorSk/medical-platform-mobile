package com.yehorsk.medical_platform_mobile.util.body

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

@Composable
fun BodyPartImage(
    image: ImageVector,
    part: BodyPart,
    selectedPart: BodyPart?,
    x: Dp,
    y: Dp,
    width: Dp,
    height: Dp,
    onClick: (BodyPart) -> Unit
) {
    Image(
        imageVector = image,
        contentDescription = part.name,

        colorFilter = ColorFilter.tint(
            if (selectedPart == part) {
                Color(0xFFF14D49)
            } else {
                Color(0xFFD9D9D9)
            }
        ),
        alignment = Alignment.TopCenter,
        modifier = Modifier
            .offset(
                x = x,
                y = y
            )
            .size(
                width = width,
                height = height
            )
            .clickable {
                onClick(part)
            }
    )
}