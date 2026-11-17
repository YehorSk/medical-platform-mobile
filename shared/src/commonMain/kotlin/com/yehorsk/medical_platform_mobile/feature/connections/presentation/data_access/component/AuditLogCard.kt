package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogAction
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActor
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActorRole
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogOutcome
import com.yehorsk.theme.AppTheme
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.add_24px
import medicalplatformmobile.shared.generated.resources.audit_added_files
import medicalplatformmobile.shared.generated.resources.audit_generic_activity
import medicalplatformmobile.shared.generated.resources.audit_outcome_blocked
import medicalplatformmobile.shared.generated.resources.audit_outcome_failed
import medicalplatformmobile.shared.generated.resources.audit_role_admin
import medicalplatformmobile.shared.generated.resources.audit_role_doctor
import medicalplatformmobile.shared.generated.resources.audit_role_patient
import medicalplatformmobile.shared.generated.resources.audit_tried_view_card
import medicalplatformmobile.shared.generated.resources.audit_viewed_card
import medicalplatformmobile.shared.generated.resources.audit_you
import medicalplatformmobile.shared.generated.resources.audit_you_update_failed
import medicalplatformmobile.shared.generated.resources.audit_you_updated_card
import medicalplatformmobile.shared.generated.resources.audit_you_viewed_card
import medicalplatformmobile.shared.generated.resources.edit_24px
import medicalplatformmobile.shared.generated.resources.history_24px
import medicalplatformmobile.shared.generated.resources.shield_24px
import medicalplatformmobile.shared.generated.resources.visibility_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant

@Composable
fun AuditLogCard(
    log: AuditLog,
    modifier: Modifier = Modifier
) {
    val isDenied = log.outcome == AuditLogOutcome.DENIED
    val isFailed = log.outcome == AuditLogOutcome.FAILED
    val isWarning = isDenied || isFailed

    val containerColor =
        if (isDenied) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surface
    val accentColor =
        if (isWarning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = log.icon(),
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = log.title(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDenied) MaterialTheme.colorScheme.onErrorContainer
                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = log.actor.subtitle(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    log.createdAt?.let {
                        Text(
                            text = it.toLocalDisplay(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (isWarning) {
                        Text(
                            text = stringResource(
                                if (isDenied) UiRes.string.audit_outcome_blocked
                                else UiRes.string.audit_outcome_failed
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onError,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.error)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AuditLog.icon(): Painter = painterResource(
    when {
        outcome == AuditLogOutcome.DENIED -> UiRes.drawable.shield_24px
        action == AuditLogAction.READ -> UiRes.drawable.visibility_24px
        action == AuditLogAction.UPDATE -> UiRes.drawable.edit_24px
        action == AuditLogAction.CREATE -> UiRes.drawable.add_24px
        else -> UiRes.drawable.history_24px
    }
)

@Composable
private fun AuditLog.title(): String {
    val name = actor.name
    return when {
        action == AuditLogAction.READ && outcome == AuditLogOutcome.DENIED ->
            stringResource(UiRes.string.audit_tried_view_card, name)
        actor.isMe && action == AuditLogAction.READ && outcome == AuditLogOutcome.SUCCESS ->
            stringResource(UiRes.string.audit_you_viewed_card)
        actor.isMe && action == AuditLogAction.UPDATE && outcome == AuditLogOutcome.SUCCESS ->
            stringResource(UiRes.string.audit_you_updated_card)
        actor.isMe && action == AuditLogAction.UPDATE && outcome == AuditLogOutcome.FAILED ->
            stringResource(UiRes.string.audit_you_update_failed)
        action == AuditLogAction.READ && outcome == AuditLogOutcome.SUCCESS ->
            stringResource(UiRes.string.audit_viewed_card, name)
        actor.role == AuditLogActorRole.DOCTOR && action == AuditLogAction.UPDATE && outcome == AuditLogOutcome.SUCCESS ->
            stringResource(UiRes.string.audit_added_files, name)
        // Unmapped combination: prefer the backend text, else a generic line
        detail.isNotBlank() -> detail
        else -> stringResource(UiRes.string.audit_generic_activity, name)
    }
}

@Composable
private fun AuditLogActor.subtitle(): String {
    if (isMe) return stringResource(UiRes.string.audit_you)
    val role = when (role) {
        AuditLogActorRole.DOCTOR -> stringResource(UiRes.string.audit_role_doctor)
        AuditLogActorRole.PATIENT -> stringResource(UiRes.string.audit_role_patient)
        AuditLogActorRole.ADMIN -> stringResource(UiRes.string.audit_role_admin)
        AuditLogActorRole.UNKNOWN -> null
    }
    return listOfNotNull(name, role).joinToString(" · ")
}

private val displayFormat = LocalDateTime.Format {
    day()
    char('.')
    monthNumber()
    char('.')
    year()
    chars(", ")
    hour()
    char(':')
    minute()
}

private fun Instant.toLocalDisplay(): String =
    toLocalDateTime(TimeZone.currentSystemDefault()).format(displayFormat)

@Preview
@Composable
private fun AuditLogCardPreview() {
    AppTheme {
        Surface {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AuditLogCard(
                    log = AuditLog(
                        id = "1",
                        action = AuditLogAction.READ,
                        outcome = AuditLogOutcome.DENIED,
                        detail = "",
                        createdAt = Instant.parse("2026-11-17T09:41:12.345Z"),
                        actor = AuditLogActor("a", "MUDr. Jan Novak", AuditLogActorRole.DOCTOR, false)
                    )
                )
                AuditLogCard(
                    log = AuditLog(
                        id = "2",
                        action = AuditLogAction.READ,
                        outcome = AuditLogOutcome.SUCCESS,
                        detail = "",
                        createdAt = Instant.parse("2026-11-16T14:05:00Z"),
                        actor = AuditLogActor("a", "MUDr. Jan Novak", AuditLogActorRole.DOCTOR, false)
                    )
                )
            }
        }
    }
}
