package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.TabToggleButton
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordTab
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.anatomy_tab
import medicalplatformmobile.shared.generated.resources.details_tab
import org.jetbrains.compose.resources.stringResource

@Composable
fun CreateRecordTabs(
    selectedTab: CreateRecordTab,
    onTabSelected: (CreateRecordTab) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0))
            .padding(4.dp)
    ) {
        Row {
            TabToggleButton(
                roundCorners = false,
                label = stringResource(UiRes.string.details_tab),
                selected = selectedTab == CreateRecordTab.DETAILS,
                onClick = { onTabSelected(CreateRecordTab.DETAILS) },
                modifier = Modifier.weight(1f)
            )
            TabToggleButton(
                roundCorners = false,
                label = stringResource(UiRes.string.anatomy_tab),
                selected = selectedTab == CreateRecordTab.ANATOMY,
                onClick = { onTabSelected(CreateRecordTab.ANATOMY) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}