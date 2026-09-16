package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.BackAnatomy
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.BodyMap
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.FrontAnatomy
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.backBodyRegions
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.common.body.frontBodyRegions
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
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isPortrait = maxHeight > maxWidth
        val isCompactWidth = maxWidth < 600.dp
        val diagramHeightFraction = if (isPortrait && isCompactWidth) 0.7f else 0.5f
        val diagramHeight = maxHeight * diagramHeightFraction

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ViewAnatomyTabs(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                selectedRegion = selectedRegion,
                onRegionSelected = { onRegionToggled(it) }
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(diagramHeight)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                val (regions, bodyVector) = when(selectedRegion){
                    BodyRegion.FRONT -> Pair(frontBodyRegions, FrontAnatomy)
                    BodyRegion.BACK -> Pair(backBodyRegions, BackAnatomy)
                }
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
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedParts.forEach { part ->
                    AssistChip(
                        onClick = { onBodyPartToggled(part) },
                        label = {
                            val partName = part.name.replace('_', ' ').lowercase().replaceFirstChar { it.uppercase() }
                            val regionLabel = when (part.region) {
                                BodyRegion.FRONT -> "Front"
                                BodyRegion.BACK -> "Back"
                            }
                            Text("$regionLabel: $partName")
                        },
                    )
                }
            }
        }
    }
}