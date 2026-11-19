package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActions
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActor
import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLogActorRole
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.audit_action_read
import medicalplatformmobile.shared.generated.resources.audit_action_unknown
import medicalplatformmobile.shared.generated.resources.audit_action_update
import medicalplatformmobile.shared.generated.resources.audit_added_files
import medicalplatformmobile.shared.generated.resources.audit_generic_activity
import medicalplatformmobile.shared.generated.resources.audit_role_admin
import medicalplatformmobile.shared.generated.resources.audit_role_doctor
import medicalplatformmobile.shared.generated.resources.audit_role_patient
import medicalplatformmobile.shared.generated.resources.audit_tried_view_card
import medicalplatformmobile.shared.generated.resources.audit_viewed_card
import medicalplatformmobile.shared.generated.resources.edit_24px
import medicalplatformmobile.shared.generated.resources.history_24px
import medicalplatformmobile.shared.generated.resources.shield_24px
import medicalplatformmobile.shared.generated.resources.visibility_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// Shared by the card access list and its details screen

val AuditLog.isFilesAdded: Boolean
    get() = action == AuditLogActions.UPDATE && outcome == AuditLogOutcome.SUCCESS

@Composable
fun AuditLog.icon(): Painter = painterResource(
    when {
        outcome.isWarning -> UiRes.drawable.shield_24px
        action == AuditLogActions.READ -> UiRes.drawable.visibility_24px
        action == AuditLogActions.UPDATE -> UiRes.drawable.edit_24px
        else -> UiRes.drawable.history_24px
    }
)

@Composable
fun AuditLog.title(): String {
    val name = actor.name
    return when {
        action == AuditLogActions.READ && outcome == AuditLogOutcome.SUCCESS ->
            stringResource(UiRes.string.audit_viewed_card, name)
        action == AuditLogActions.READ && outcome == AuditLogOutcome.DENIED ->
            stringResource(UiRes.string.audit_tried_view_card, name)
        isFilesAdded ->
            stringResource(UiRes.string.audit_added_files, name)
        // Unmapped combination: the backend's English text is the fallback
        detail.isNotBlank() -> detail
        else -> stringResource(UiRes.string.audit_generic_activity, name)
    }
}

@Composable
fun AuditLogActor.roleLabel(): String? = when (role) {
    AuditLogActorRole.DOCTOR -> stringResource(UiRes.string.audit_role_doctor)
    AuditLogActorRole.PATIENT -> stringResource(UiRes.string.audit_role_patient)
    AuditLogActorRole.ADMIN -> stringResource(UiRes.string.audit_role_admin)
    AuditLogActorRole.UNKNOWN -> null
}

@Composable
fun AuditLogActor.subtitle(): String = listOfNotNull(name, roleLabel()).joinToString(" · ")

@Composable
fun AuditLog.actionLabel(): String = stringResource(
    when (action) {
        AuditLogActions.READ -> UiRes.string.audit_action_read
        AuditLogActions.UPDATE -> UiRes.string.audit_action_update
        else -> UiRes.string.audit_action_unknown
    }
)
