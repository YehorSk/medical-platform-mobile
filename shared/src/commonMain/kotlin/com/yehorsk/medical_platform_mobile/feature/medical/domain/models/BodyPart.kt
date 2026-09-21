package com.yehorsk.medical_platform_mobile.feature.medical.domain.models

import androidx.compose.ui.graphics.Path
import com.yehorsk.medical_platform_mobile.util.UiText
import com.yehorsk.medical_platform_mobile.util.UiText.Resource
import medicalplatformmobile.shared.generated.resources.UiRes.string
import medicalplatformmobile.shared.generated.resources.body_part_abdomen
import medicalplatformmobile.shared.generated.resources.body_part_back
import medicalplatformmobile.shared.generated.resources.body_part_buttocks
import medicalplatformmobile.shared.generated.resources.body_part_chest
import medicalplatformmobile.shared.generated.resources.body_part_head
import medicalplatformmobile.shared.generated.resources.body_part_left_ankle
import medicalplatformmobile.shared.generated.resources.body_part_left_arm
import medicalplatformmobile.shared.generated.resources.body_part_left_calf
import medicalplatformmobile.shared.generated.resources.body_part_left_elbow
import medicalplatformmobile.shared.generated.resources.body_part_left_foot
import medicalplatformmobile.shared.generated.resources.body_part_left_forearm
import medicalplatformmobile.shared.generated.resources.body_part_left_hamstring
import medicalplatformmobile.shared.generated.resources.body_part_left_hand
import medicalplatformmobile.shared.generated.resources.body_part_left_knee
import medicalplatformmobile.shared.generated.resources.body_part_left_leg
import medicalplatformmobile.shared.generated.resources.body_part_left_shoulder
import medicalplatformmobile.shared.generated.resources.body_part_left_sole
import medicalplatformmobile.shared.generated.resources.body_part_left_thigh
import medicalplatformmobile.shared.generated.resources.body_part_left_upper_arm
import medicalplatformmobile.shared.generated.resources.body_part_left_wrist
import medicalplatformmobile.shared.generated.resources.body_part_loin
import medicalplatformmobile.shared.generated.resources.body_part_neck
import medicalplatformmobile.shared.generated.resources.body_part_pelvis
import medicalplatformmobile.shared.generated.resources.body_part_right_ankle
import medicalplatformmobile.shared.generated.resources.body_part_right_arm
import medicalplatformmobile.shared.generated.resources.body_part_right_calf
import medicalplatformmobile.shared.generated.resources.body_part_right_elbow
import medicalplatformmobile.shared.generated.resources.body_part_right_foot
import medicalplatformmobile.shared.generated.resources.body_part_right_forearm
import medicalplatformmobile.shared.generated.resources.body_part_right_hamstring
import medicalplatformmobile.shared.generated.resources.body_part_right_hand
import medicalplatformmobile.shared.generated.resources.body_part_right_knee
import medicalplatformmobile.shared.generated.resources.body_part_right_leg
import medicalplatformmobile.shared.generated.resources.body_part_right_shoulder
import medicalplatformmobile.shared.generated.resources.body_part_right_sole
import medicalplatformmobile.shared.generated.resources.body_part_right_thigh
import medicalplatformmobile.shared.generated.resources.body_part_right_upper_arm
import medicalplatformmobile.shared.generated.resources.body_part_right_wrist

enum class BodyPart(val text: UiText) {

    // BOTH
    RIGHT_HAND(Resource(string.body_part_right_hand)),
    LEFT_HAND(Resource(string.body_part_left_hand)),

    LEFT_FOREARM(Resource(string.body_part_left_forearm)),
    RIGHT_FOREARM(Resource(string.body_part_right_forearm)),

    RIGHT_UPPER_ARM(Resource(string.body_part_right_upper_arm)),
    LEFT_UPPER_ARM(Resource(string.body_part_left_upper_arm)),

    LEFT_SHOULDER(Resource(string.body_part_left_shoulder)),
    RIGHT_SHOULDER(Resource(string.body_part_right_shoulder)),

    NECK(Resource(string.body_part_neck)),
    HEAD(Resource(string.body_part_head)),

    LEFT_KNEE(Resource(string.body_part_left_knee)),
    RIGHT_KNEE(Resource(string.body_part_right_knee)),

    LEFT_THIGH(Resource(string.body_part_left_thigh)),
    RIGHT_THIGH(Resource(string.body_part_right_thigh)),

    LEFT_FOOT(Resource(string.body_part_left_foot)),
    RIGHT_FOOT(Resource(string.body_part_right_foot)),

    LEFT_ANKLE(Resource(string.body_part_left_ankle)),
    RIGHT_ANKLE(Resource(string.body_part_right_ankle)),

    LEFT_LEG(Resource(string.body_part_left_leg)),
    RIGHT_LEG(Resource(string.body_part_right_leg)),

    LEFT_WRIST(Resource(string.body_part_left_wrist)),
    RIGHT_WRIST(Resource(string.body_part_right_wrist)),

    LEFT_ELBOW(Resource(string.body_part_left_elbow)),
    RIGHT_ELBOW(Resource(string.body_part_right_elbow)),

    // FRONT
    CHEST(Resource(string.body_part_chest)),
    ABDOMEN(Resource(string.body_part_abdomen)),
    PELVIS(Resource(string.body_part_pelvis)),

    // BACK
    BACK(Resource(string.body_part_back)),
    LOIN(Resource(string.body_part_loin)),
    BUTTOCKS(Resource(string.body_part_buttocks)),

    LEFT_ARM(Resource(string.body_part_left_arm)),
    RIGHT_ARM(Resource(string.body_part_right_arm)),

    LEFT_HAMSTRING(Resource(string.body_part_left_hamstring)),
    RIGHT_HAMSTRING(Resource(string.body_part_right_hamstring)),

    LEFT_CALF(Resource(string.body_part_left_calf)),
    RIGHT_CALF(Resource(string.body_part_right_calf)),

    LEFT_SOLE(Resource(string.body_part_left_sole)),
    RIGHT_SOLE(Resource(string.body_part_right_sole))
}

data class BodyHitRegion(
    val name: String,
    val part: BodyPart,
    val path: Path,
    val region: BodyRegion
)

enum class BodyRegion {
    FRONT, BACK
}