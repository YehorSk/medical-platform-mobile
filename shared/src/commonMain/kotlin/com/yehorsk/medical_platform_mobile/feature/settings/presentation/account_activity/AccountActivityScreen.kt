package com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import com.yehorsk.medical_platform_mobile.core.ui.components.cards.DefaultActivityCard
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.other.DefaultEmptyState
import com.yehorsk.medical_platform_mobile.core.ui.components.other.DefaultOutcomeBadge
import com.yehorsk.medical_platform_mobile.core.ui.components.other.NoConnectionBanner
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEvent
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEventType
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthFailureReason
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.util.detailLines
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.util.icon
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.util.title
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.viewmodel.AccountActivityAction
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.viewmodel.AccountActivityState
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.viewmodel.AccountActivityViewModel
import com.yehorsk.medical_platform_mobile.util.toLocalShortDisplay
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.account_activity
import medicalplatformmobile.shared.generated.resources.account_activity_empty
import medicalplatformmobile.shared.generated.resources.account_activity_hint
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant

@Composable
fun AccountActivityScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountActivityViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    AccountActivityScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                AccountActivityAction.OnGoBackClicked -> onGoBackClicked()
            }
        }
    )
}

@Composable
fun AccountActivityScreenRoot(
    modifier: Modifier = Modifier,
    state: AccountActivityState,
    onAction: (AccountActivityAction) -> Unit
) {
    val columns = if (currentWindowLayout().isExpanded) 2 else 1

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.account_activity),
            showGoBackButton = true,
            onGoBackClicked = { onAction(AccountActivityAction.OnGoBackClicked) }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                !state.isConnected -> NoConnectionBanner(modifier = Modifier.fillMaxSize())
                !state.isLoading && state.events.isEmpty() ->
                    DefaultEmptyState(stringResource(UiRes.string.account_activity_empty))
                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = stringResource(UiRes.string.account_activity_hint),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                    items(state.events, key = { it.id }) { event ->
                        DefaultActivityCard(
                            icon = event.icon(),
                            title = event.title(),
                            details = event.detailLines(),
                            timestamp = event.createdAt?.toLocalShortDisplay(),
                            highlighted = event.outcome.isWarning,
                            badges = { DefaultOutcomeBadge(outcome = event.outcome) }
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
private fun AccountActivityScreenPreview() {
    AppTheme {
        AccountActivityScreenRoot(
            state = AccountActivityState(
                events = listOf(
                    AuthEvent(
                        id = "1",
                        type = AuthEventType.LOGIN,
                        outcome = AuditLogOutcome.FAILED,
                        failureReason = AuthFailureReason.WRONG_PASSWORD,
                        ipAddress = "192.168.0.12",
                        userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
                        createdAt = Instant.parse("2026-11-18T21:14:03Z")
                    ),
                    AuthEvent(
                        id = "2",
                        type = AuthEventType.LOGIN,
                        outcome = AuditLogOutcome.SUCCESS,
                        failureReason = null,
                        ipAddress = "10.0.2.2",
                        userAgent = "okhttp/4.12.0",
                        createdAt = Instant.parse("2026-11-18T08:02:41Z")
                    )
                )
            ),
            onAction = {}
        )
    }
}
