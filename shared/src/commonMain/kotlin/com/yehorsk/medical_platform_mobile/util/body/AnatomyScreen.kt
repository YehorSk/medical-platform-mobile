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
import com.yehorsk.medical_platform_mobile.util.body.vectors.Abdomen
import com.yehorsk.medical_platform_mobile.util.body.vectors.Chest
import com.yehorsk.medical_platform_mobile.util.body.vectors.Head
import com.yehorsk.medical_platform_mobile.util.body.vectors.LeftArm
import com.yehorsk.medical_platform_mobile.util.body.vectors.LeftFoot
import com.yehorsk.medical_platform_mobile.util.body.vectors.LeftHand
import com.yehorsk.medical_platform_mobile.util.body.vectors.LeftLeg
import com.yehorsk.medical_platform_mobile.util.body.vectors.LeftShoulder
import com.yehorsk.medical_platform_mobile.util.body.vectors.Neck
import com.yehorsk.medical_platform_mobile.util.body.vectors.Orbit
import com.yehorsk.medical_platform_mobile.util.body.vectors.RightArm
import com.yehorsk.medical_platform_mobile.util.body.vectors.RightFoot
import com.yehorsk.medical_platform_mobile.util.body.vectors.RightHand
import com.yehorsk.medical_platform_mobile.util.body.vectors.RightLeg
import com.yehorsk.medical_platform_mobile.util.body.vectors.RightShoulder
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

            // Head
            BodyPartImage(
                image = Head,
                part = BodyPart.HEAD,
                selectedPart = selectedPart,
                x = (-27).dp,
                y = (-6).dp,
                width = 80.3.dp,
                height = 100.dp,
                onClick = onPartClick
            )

            // Eyes
            BodyPartImage(
                image = Orbit,
                part = BodyPart.ORBIT,
                selectedPart = selectedPart,
                x = (-6).dp,
                y = 27.dp,
                width = 40.dp,
                height = 10.dp,
                onClick = onPartClick
            )

            // Neck
            BodyPartImage(
                image = Neck,
                part = BodyPart.NECK,
                selectedPart = selectedPart,
                x = (-21).dp,
                y = 70.dp,
                width = 70.dp,
                height = 80.dp,
                onClick = onPartClick
            )

            // Right shoulder
            BodyPartImage(
                image = RightShoulder,
                part = BodyPart.RIGHT_SHOULDER,
                selectedPart = selectedPart,
                x = (-90).dp,
                y = 100.dp,
                width = 75.dp,
                height = 110.dp,
                onClick = onPartClick
            )

            // Left shoulder
            BodyPartImage(
                image = LeftShoulder,
                part = BodyPart.LEFT_SHOULDER,
                selectedPart = selectedPart,
                x = 48.dp,
                y = 95.dp,
                width = 100.dp,
                height = 110.dp,
                onClick = onPartClick
            )

            // Chest
            BodyPartImage(
                image = Chest,
                part = BodyPart.CHEST,
                selectedPart = selectedPart,
                x = (-57).dp,
                y = 140.dp,
                width = 150.dp,
                height = 80.dp,
                onClick = onPartClick
            )

            // Right arm
            BodyPartImage(
                image = RightArm,
                part = BodyPart.RIGHT_ARM,
                selectedPart = selectedPart,
                x = (-162).dp,
                y = 210.dp,
                width = 100.dp,
                height = 190.dp,
                onClick = onPartClick
            )

            // Left arm
            BodyPartImage(
                image = LeftArm,
                part = BodyPart.LEFT_ARM,
                selectedPart = selectedPart,
                x = 100.dp,
                y = 202.dp,
                width = 100.dp,
                height = 190.dp,
                onClick = onPartClick
            )

            // Abdomen
            BodyPartImage(
                image = Abdomen,
                part = BodyPart.ABDOMEN,
                selectedPart = selectedPart,
                x = (-70).dp,
                y = 210.dp,
                width = 180.dp,
                height = 230.dp,
                onClick = onPartClick
            )

            // Right hand
            BodyPartImage(
                image = RightHand,
                part = BodyPart.RIGHT_HAND,
                selectedPart = selectedPart,
                x = (-197).dp,
                y = 387.dp,
                width = 60.dp,
                height = 90.dp,
                onClick = onPartClick
            )

            // Left hand
            BodyPartImage(
                image = LeftHand,
                part = BodyPart.LEFT_HAND,
                selectedPart = selectedPart,
                x = 172.dp,
                y = 336.dp,
                width = 60.dp,
                height = 170.dp,
                onClick = onPartClick
            )

            // Right leg
            BodyPartImage(
                image = RightLeg,
                part = BodyPart.RIGHT_LEG,
                selectedPart = selectedPart,
                x = (-110).dp,
                y = 420.dp,
                width = 162.dp,
                height = 350.dp,
                onClick = onPartClick
            )

            // Left leg
            BodyPartImage(
                image = LeftLeg,
                part = BodyPart.LEFT_LEG,
                selectedPart = selectedPart,
                x = (-10).dp,
                y = 415.dp,
                width = 156.dp,
                height = 350.dp,
                onClick = onPartClick
            )

            // Right foot
            BodyPartImage(
                image = RightFoot,
                part = BodyPart.RIGHT_FOOT,
                selectedPart = selectedPart,
                x = (-70).dp,
                y = 773.dp,
                width = 60.dp,
                height = 50.dp,
                onClick = onPartClick
            )

            // Left foot
            BodyPartImage(
                image = LeftFoot,
                part = BodyPart.LEFT_FOOT,
                selectedPart = selectedPart,
                x = 40.dp,
                y = 771.dp,
                width = 60.dp,
                height = 50.dp,
                onClick = onPartClick
            )
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