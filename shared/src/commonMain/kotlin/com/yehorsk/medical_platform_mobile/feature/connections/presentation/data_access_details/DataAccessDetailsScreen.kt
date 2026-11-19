package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.cards.DefaultContentCard
import com.yehorsk.medical_platform_mobile.core.ui.components.cards.DefaultInfoCard
import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.other.DefaultEmptyState
import com.yehorsk.medical_platform_mobile.core.ui.components.other.DefaultOutcomeBadge
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActions
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActor
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActorRole
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.actionLabel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.icon
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.isFilesAdded
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.roleLabel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common.title
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel.DataAccessViewModel
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access_details.viewmodel.DataAccessDetailsAction
import com.yehorsk.medical_platform_mobile.util.formatTimeAgo
import com.yehorsk.medical_platform_mobile.util.toLocalFullDisplay
import com.yehorsk.medical_platform_mobile.util.toText
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.TimeZone
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.audit_details_title
import medicalplatformmobile.shared.generated.resources.audit_log_not_found
import medicalplatformmobile.shared.generated.resources.audit_manage_access
import medicalplatformmobile.shared.generated.resources.audit_note_denied
import medicalplatformmobile.shared.generated.resources.audit_note_doctor_access
import medicalplatformmobile.shared.generated.resources.audit_reference_hint
import medicalplatformmobile.shared.generated.resources.audit_resource_medical_card
import medicalplatformmobile.shared.generated.resources.audit_resource_medical_record
import medicalplatformmobile.shared.generated.resources.audit_section_reference
import medicalplatformmobile.shared.generated.resources.audit_section_what
import medicalplatformmobile.shared.generated.resources.audit_section_when
import medicalplatformmobile.shared.generated.resources.audit_section_who
import medicalplatformmobile.shared.generated.resources.audit_time_zone
import medicalplatformmobile.shared.generated.resources.calendar_today_24px
import medicalplatformmobile.shared.generated.resources.history_24px
import medicalplatformmobile.shared.generated.resources.medical_information_24px
import medicalplatformmobile.shared.generated.resources.person_24px
import medicalplatformmobile.shared.generated.resources.shield_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant

/**
 * There is no single-entry endpoint, so the screen reads the entry from the list's
 * [DataAccessViewModel] (scoped to the list's back stack entry in the nav graph).
 */
@Composable
fun DataAccessDetailsScreen(
    logId: String,
    viewModel: DataAccessViewModel,
    modifier: Modifier = Modifier,
    onGoBackClicked: () -> Unit,
    onManageAccessClicked: (doctorId: String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DataAccessDetailsScreenRoot(
        modifier = modifier,
        log = state.logs.find { it.id == logId },
        isLoading = state.isLoading,
        onAction = { action ->
            when (action) {
                DataAccessDetailsAction.OnGoBackClicked -> onGoBackClicked()
                is DataAccessDetailsAction.OnManageAccessClicked -> onManageAccessClicked(action.doctorId)
            }
        }
    )
}

@Composable
fun DataAccessDetailsScreenRoot(
    modifier: Modifier = Modifier,
    log: AuditLog?,
    isLoading: Boolean,
    onAction: (DataAccessDetailsAction) -> Unit
) {
    val columns = if (currentWindowLayout().isExpanded) 2 else 1

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.audit_details_title),
            showGoBackButton = true,
            onGoBackClicked = { onAction(DataAccessDetailsAction.OnGoBackClicked) }
        )
        when {
            log != null -> LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    SummaryCard(log)
                }
                item { WhoCard(log.actor) }
                item { WhenCard(log.createdAt) }
                item { WhatCard(log) }
                item { ReferenceCard(log.id) }
                log.noteRes()?.let { note ->
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        NoteCard(text = stringResource(note), isWarning = log.outcome.isWarning)
                    }
                }
                log.actor.doctorId?.let { doctorId ->
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        DefaultButton(
                            text = stringResource(UiRes.string.audit_manage_access),
                            leadingIcon = painterResource(UiRes.drawable.shield_24px),
                            onClick = { onAction(DataAccessDetailsAction.OnManageAccessClicked(doctorId)) }
                        )
                    }
                }
            }
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            else -> DefaultEmptyState(stringResource(UiRes.string.audit_log_not_found))
        }
    }
}

@Composable
private fun SummaryCard(log: AuditLog) {
    val isWarning = log.outcome.isWarning
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = if (isWarning) {
            CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        } else CardDefaults.elevatedCardColors()
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = log.icon(),
                    contentDescription = null,
                    tint = if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(30.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = log.title(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isWarning) MaterialTheme.colorScheme.onErrorContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DefaultOutcomeBadge(outcome = log.outcome, showSuccess = true)
                    log.createdAt?.let {
                        Text(
                            text = formatTimeAgo(it.toString()).toText(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WhoCard(actor: AuditLogActor) {
    DefaultInfoCard(
        iconRes = painterResource(UiRes.drawable.person_24px),
        title = stringResource(UiRes.string.audit_section_who),
        content = actor.name,
        secondContent = actor.roleLabel()
    )
}

@Composable
private fun WhenCard(createdAt: Instant?) {
    DefaultInfoCard(
        iconRes = painterResource(UiRes.drawable.calendar_today_24px),
        title = stringResource(UiRes.string.audit_section_when),
        content = createdAt?.toLocalFullDisplay() ?: "-",
        secondContent = stringResource(UiRes.string.audit_time_zone, TimeZone.currentSystemDefault().id)
    )
}

@Composable
private fun WhatCard(log: AuditLog) {
    DefaultInfoCard(
        iconRes = painterResource(UiRes.drawable.medical_information_24px),
        title = stringResource(UiRes.string.audit_section_what),
        content = log.actionLabel(),
        secondContent = stringResource(
            if (log.isFilesAdded) UiRes.string.audit_resource_medical_record
            else UiRes.string.audit_resource_medical_card
        )
    )
}

@Composable
private fun ReferenceCard(id: String) {
    DefaultContentCard(
        title = stringResource(UiRes.string.audit_section_reference),
        leadingIcon = painterResource(UiRes.drawable.history_24px)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            SelectionContainer {
                Text(
                    text = id,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = stringResource(UiRes.string.audit_reference_hint),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun NoteCard(text: String, isWarning: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isWarning) MaterialTheme.colorScheme.errorContainer
                else MaterialTheme.colorScheme.secondaryContainer
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val contentColor = if (isWarning) MaterialTheme.colorScheme.onErrorContainer
        else MaterialTheme.colorScheme.onSecondaryContainer
        Icon(
            painter = painterResource(UiRes.drawable.shield_24px),
            contentDescription = null,
            tint = contentColor
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor
        )
    }
}

private fun AuditLog.noteRes() = when {
    outcome == AuditLogOutcome.DENIED -> UiRes.string.audit_note_denied
    actor.role == AuditLogActorRole.DOCTOR && outcome == AuditLogOutcome.SUCCESS ->
        UiRes.string.audit_note_doctor_access
    else -> null
}

@Preview
@Composable
private fun DataAccessDetailsScreenPreview() {
    AppTheme {
        DataAccessDetailsScreenRoot(
            log = AuditLog(
                id = "8f0c2a4e-1b7d-4e2a-9c51-3d6f0a9b1e22",
                action = AuditLogActions.READ,
                outcome = AuditLogOutcome.DENIED,
                detail = "",
                createdAt = Instant.parse("2026-11-17T09:41:12.345Z"),
                actor = AuditLogActor(id = "a", doctorId = "d", name = "MUDr. Jan Novak", role = AuditLogActorRole.DOCTOR)
            ),
            isLoading = false,
            onAction = {}
        )
    }
}
