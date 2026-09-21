package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.util.WindowLayout
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordAction
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordScreenState
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordTab

@Composable
fun CreateMedicalRecordScreenLayout(
    state: CreateRecordScreenState,
    onAction: (CreateRecordAction) -> Unit,
    modifier: Modifier = Modifier,
    windowLayout: WindowLayout = currentWindowLayout()
) {
    if (windowLayout.isExpanded) {
        TwoPaneLayout(state = state, onAction = onAction, modifier = modifier)
    } else {
        TabbedLayout(state = state, onAction = onAction, modifier = modifier)
    }
}

@Composable
private fun TwoPaneLayout(
    state: CreateRecordScreenState,
    onAction: (CreateRecordAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            DetailsPane(state = state, onAction = onAction)
        }
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            RecordAnatomyPane(state = state, onAction = onAction)
        }
    }
}

@Composable
private fun TabbedLayout(
    state: CreateRecordScreenState,
    onAction: (CreateRecordAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        CreateRecordTabs(
            selectedTab = state.currentTab,
            onTabSelected = { onAction(CreateRecordAction.OnTabSelected(it)) }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            AnimatedContent(
                targetState = state.currentTab,
                modifier = Modifier.fillMaxSize(),
                transitionSpec = {
                    val direction = if (targetState.ordinal > initialState.ordinal) {
                        AnimatedContentTransitionScope.SlideDirection.Left
                    } else {
                        AnimatedContentTransitionScope.SlideDirection.Right
                    }
                    (slideIntoContainer(direction, animationSpec = tween(300)) + fadeIn(tween(300)))
                        .togetherWith(
                            slideOutOfContainer(direction, animationSpec = tween(300)) + fadeOut(tween(300))
                        )
                },
                label = "create_record_tab_switch"
            ) { tab ->
                when (tab) {
                    CreateRecordTab.DETAILS -> DetailsPane(state = state, onAction = onAction)
                    CreateRecordTab.ANATOMY -> RecordAnatomyPane(state = state, onAction = onAction)
                }
            }
        }
    }
}

@Composable
private fun RecordAnatomyPane(
    state: CreateRecordScreenState,
    onAction: (CreateRecordAction) -> Unit,
    modifier: Modifier = Modifier
) {
    AnatomyPane(
        selectedRegion = state.selectedRegion,
        selectedParts = state.form.selectedBodyParts,
        onBodyPartToggled = { onAction(CreateRecordAction.OnBodyPartSelected(it)) },
        onRegionToggled = { onAction(CreateRecordAction.OnBodyRegionSelected(it)) },
        modifier = modifier.fillMaxSize()
    )
}