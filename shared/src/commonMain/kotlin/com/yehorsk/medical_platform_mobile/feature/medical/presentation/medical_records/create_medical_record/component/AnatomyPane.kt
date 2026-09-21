package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.common.body.BackAnatomy
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.common.body.BodyMap
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.common.body.FrontAnatomy
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.common.body.backBodyRegions
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.common.body.frontBodyRegions
import kotlin.collections.forEach

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AnatomyPane(
    selectedRegion: BodyRegion,
    selectedParts: List<BodyHitRegion>,
    onBodyPartToggled: (BodyHitRegion) -> Unit,
    onRegionToggled: (BodyRegion) -> Unit,
    modifier: Modifier = Modifier,
) {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    val diagramHeight = remember(windowInfo.containerSize, density) {
        with(density) { (windowInfo.containerSize.height * 0.7f).toDp() }
    }.coerceAtMost(600.dp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ViewAnatomyTabs(
            modifier = Modifier.padding(bottom = 8.dp),
            selectedRegion = selectedRegion,
            onRegionSelected = { onRegionToggled(it) }
        )

        val (regions, bodyVector) = when (selectedRegion) {
            BodyRegion.FRONT -> Pair(frontBodyRegions, FrontAnatomy)
            BodyRegion.BACK -> Pair(backBodyRegions, BackAnatomy)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(diagramHeight)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            BodyMap(
                regions = regions,
                bodyVector = bodyVector,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(
                        bodyVector.viewportWidth / bodyVector.viewportHeight,
                        matchHeightConstraintsFirst = true
                    ),
                selected = selectedParts,
                onPartSelected = { region ->
                    val hit = region ?: return@BodyMap
                    onBodyPartToggled(hit)
                }
            )
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            selectedParts.forEach { part ->
                AssistChip(
                    onClick = { onBodyPartToggled(part) },
                    modifier = Modifier.height(28.dp),
                    colors = AssistChipDefaults.assistChipColors(),
                    border = AssistChipDefaults.assistChipBorder(enabled = true),
                    label = {
                        val partName = part.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() }
                        Text(
                            text = partName,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    leadingIcon = {
                        val regionLetter = when (part.region) {
                            BodyRegion.FRONT -> "F"
                            BodyRegion.BACK -> "B"
                        }
                        Text(
                            text = regionLetter,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }
    }
}