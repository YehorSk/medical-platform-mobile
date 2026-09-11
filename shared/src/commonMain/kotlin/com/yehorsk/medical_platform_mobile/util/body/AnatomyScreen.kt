package com.yehorsk.medical_platform_mobile.util.body

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme

@Composable
fun HumanBody(
    selectedPart: BodyPart?,
    onPartClick: (BodyPart) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(830.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(830.dp)
        ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HumanBodyPreview() {
    AppTheme {
        HumanBody(
            selectedPart = BodyPart.HEAD,
            onPartClick = {}
        )
    }
}

enum class BodyPart {
    HEAD,
    ORBIT,
    NECK,
    CHEST,
    RIGHT_SHOULDER,
    RIGHT_ARM,
    RIGHT_HAND,
    LEFT_SHOULDER,
    LEFT_ARM,
    LEFT_HAND,
    ABDOMEN,
    RIGHT_LEG,
    RIGHT_FOOT,
    LEFT_LEG,
    LEFT_FOOT
}