package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.TabToggleButton
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.anatomy_back_view
import medicalplatformmobile.shared.generated.resources.anatomy_front_view
import org.jetbrains.compose.resources.stringResource

@Composable
fun ViewAnatomyTabs(
    modifier: Modifier = Modifier,
    selectedRegion: BodyRegion,
    onRegionSelected: (BodyRegion) -> Unit
){
    Box(
        modifier = modifier
            .background(Color(0xFFF0F0F0), RoundedCornerShape(50.dp))
            .padding(4.dp)
    ) {
        Row {
            TabToggleButton(
                label = stringResource(UiRes.string.anatomy_front_view),
                selected = selectedRegion == BodyRegion.FRONT,
                onClick = { onRegionSelected(BodyRegion.FRONT) },
                modifier = Modifier.weight(1f)
            )
            TabToggleButton(
                label = stringResource(UiRes.string.anatomy_back_view),
                selected = selectedRegion == BodyRegion.BACK,
                onClick = { onRegionSelected(BodyRegion.BACK) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}