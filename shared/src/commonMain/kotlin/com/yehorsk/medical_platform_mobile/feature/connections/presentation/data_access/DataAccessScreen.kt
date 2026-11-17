package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.other.NoConnectionBanner
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.component.AuditLogCard
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel.DataAccessAction
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel.DataAccessState
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel.DataAccessViewModel
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
    onGoBackClicked: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DataAccessScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                DataAccessAction.OnGoBackClicked -> onGoBackClicked()
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
                !state.hasMedicalCard -> CenteredMessage(stringResource(UiRes.string.audit_no_medical_card))
                !state.isLoading && state.logs.isEmpty() -> CenteredMessage(stringResource(UiRes.string.audit_empty))
                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(state.logs, key = { it.id }) { log ->
                        AuditLogCard(log = log)
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

@Composable
private fun CenteredMessage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
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
