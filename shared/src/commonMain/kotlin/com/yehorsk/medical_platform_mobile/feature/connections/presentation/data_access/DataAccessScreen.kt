package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.cards.DefaultActivityCard
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.other.DefaultEmptyState
import com.yehorsk.medical_platform_mobile.core.ui.components.other.DefaultOutcomeBadge
import com.yehorsk.medical_platform_mobile.core.ui.components.other.NoConnectionBanner
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.icon
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.subtitle
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.title
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel.DataAccessAction
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel.DataAccessState
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel.DataAccessViewModel
import com.yehorsk.medical_platform_mobile.util.toLocalShortDisplay
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.audit_empty
import medicalplatformmobile.shared.generated.resources.audit_no_medical_card
import medicalplatformmobile.shared.generated.resources.data_access
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DataAccessScreen(
    modifier: Modifier = Modifier,
    viewModel: DataAccessViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit,
    onLogClicked: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DataAccessScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                DataAccessAction.OnGoBackClicked -> onGoBackClicked()
                is DataAccessAction.OnLogClicked -> onLogClicked(action.logId)
            }
        }
    )
}

@Composable
fun DataAccessScreenRoot(
    modifier: Modifier = Modifier,
    state: DataAccessState,
    onAction: (DataAccessAction) -> Unit
) {
    val columns = if (currentWindowLayout().isExpanded) 2 else 1

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.data_access),
            showGoBackButton = true,
            onGoBackClicked = { onAction(DataAccessAction.OnGoBackClicked) }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                !state.isConnected -> NoConnectionBanner(modifier = Modifier.fillMaxSize())
                !state.hasMedicalCard -> DefaultEmptyState(stringResource(UiRes.string.audit_no_medical_card))
                !state.isLoading && state.logs.isEmpty() -> DefaultEmptyState(stringResource(UiRes.string.audit_empty))
                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.logs, key = { it.id }) { log ->
                        DefaultActivityCard(
                            icon = log.icon(),
                            title = log.title(),
                            details = listOf(log.actor.subtitle()),
                            timestamp = log.createdAt?.toLocalShortDisplay(),
                            highlighted = log.outcome.isWarning,
                            onClick = { onAction(DataAccessAction.OnLogClicked(log.id)) },
                            badges = { DefaultOutcomeBadge(outcome = log.outcome) }
                        )
                    }
                }
            }
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview
@Composable
private fun DataAccessScreenRootPreview() {
    AppTheme {
        DataAccessScreenRoot(
            state = DataAccessState(),
            onAction = {}
        )
    }
}
