package com.yehorsk.medical_platform_mobile.core.ui.components.other

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.audit_outcome_allowed
import medicalplatformmobile.shared.generated.resources.audit_outcome_blocked
import medicalplatformmobile.shared.generated.resources.audit_outcome_failed
import org.jetbrains.compose.resources.stringResource

/** Blocked / Failed badge for audit entries; SUCCESS shows "Allowed" only when [showSuccess] */
@Composable
fun DefaultOutcomeBadge(
    outcome: AuditLogOutcome,
    modifier: Modifier = Modifier,
    showSuccess: Boolean = false
) {
    when (outcome) {
        AuditLogOutcome.DENIED, AuditLogOutcome.FAILED -> DefaultStatusBadge(
            modifier = modifier,
            text = stringResource(
                if (outcome == AuditLogOutcome.DENIED) UiRes.string.audit_outcome_blocked
                else UiRes.string.audit_outcome_failed
            ),
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        )
        AuditLogOutcome.SUCCESS -> if (showSuccess) {
            DefaultStatusBadge(
                modifier = modifier,
                text = stringResource(UiRes.string.audit_outcome_allowed)
            )
        }
        AuditLogOutcome.UNKNOWN -> {}
    }
}
