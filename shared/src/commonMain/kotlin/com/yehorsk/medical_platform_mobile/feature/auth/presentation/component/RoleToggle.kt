package com.yehorsk.medical_platform_mobile.feature.auth.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.auth.domain.model.UserRole
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.person_24px
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun RoleToggle(
    selectedRole: UserRole,
    onRoleSelected: (UserRole) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(50.dp))
            .padding(4.dp)
    ) {
        Row {
            RoleToggleButton(
                label = "Patient",
                icon = painterResource(UiRes.drawable.person_24px),
                selected = selectedRole == UserRole.PATIENT,
                onClick = { onRoleSelected(UserRole.PATIENT) },
                modifier = Modifier.weight(1f)
            )
            RoleToggleButton(
                label = "Doctor",
                icon = painterResource(UiRes.drawable.stethoscope_24px),
                selected = selectedRole == UserRole.DOCTOR,
                onClick = { onRoleSelected(UserRole.DOCTOR) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}