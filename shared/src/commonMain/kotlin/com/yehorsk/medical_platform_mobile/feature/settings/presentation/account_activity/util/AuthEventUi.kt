package com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.yehorsk.medical_platform_mobile.core.domain.model.AuditLogOutcome
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEvent
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthEventType
import com.yehorsk.medical_platform_mobile.feature.settings.domain.model.AuthFailureReason
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.account_activity
import medicalplatformmobile.shared.generated.resources.account_circle_24px
import medicalplatformmobile.shared.generated.resources.arrow_back_24px
import medicalplatformmobile.shared.generated.resources.auth_blocked_doctor_not_approved
import medicalplatformmobile.shared.generated.resources.auth_blocked_email_not_verified
import medicalplatformmobile.shared.generated.resources.auth_device_app
import medicalplatformmobile.shared.generated.resources.auth_device_browser
import medicalplatformmobile.shared.generated.resources.auth_failed_sign_in
import medicalplatformmobile.shared.generated.resources.auth_failed_wrong_password
import medicalplatformmobile.shared.generated.resources.auth_ip_address
import medicalplatformmobile.shared.generated.resources.auth_password_change_failed
import medicalplatformmobile.shared.generated.resources.auth_password_changed
import medicalplatformmobile.shared.generated.resources.auth_reset_code_confirmed
import medicalplatformmobile.shared.generated.resources.auth_reset_code_expired
import medicalplatformmobile.shared.generated.resources.auth_reset_code_failed
import medicalplatformmobile.shared.generated.resources.auth_reset_code_too_many
import medicalplatformmobile.shared.generated.resources.auth_reset_code_wrong
import medicalplatformmobile.shared.generated.resources.auth_reset_requested
import medicalplatformmobile.shared.generated.resources.auth_signed_in
import medicalplatformmobile.shared.generated.resources.auth_signed_out
import medicalplatformmobile.shared.generated.resources.check_24px
import medicalplatformmobile.shared.generated.resources.history_24px
import medicalplatformmobile.shared.generated.resources.mail_24px
import medicalplatformmobile.shared.generated.resources.shield_24px
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthEvent.icon(): Painter = painterResource(
    when {
        outcome.isWarning -> UiRes.drawable.shield_24px
        type == AuthEventType.LOGIN -> UiRes.drawable.account_circle_24px
        type == AuthEventType.LOGOUT -> UiRes.drawable.arrow_back_24px
        type == AuthEventType.PASSWORD_RESET_REQUESTED -> UiRes.drawable.mail_24px
        type == AuthEventType.PASSWORD_RESET -> UiRes.drawable.check_24px
        else -> UiRes.drawable.history_24px
    }
)

@Composable
fun AuthEvent.title(): String = stringResource(titleRes())

private fun AuthEvent.titleRes(): StringResource {
    val success = outcome == AuditLogOutcome.SUCCESS
    return when (type) {
        AuthEventType.LOGIN -> when {
            success -> UiRes.string.auth_signed_in
            failureReason == AuthFailureReason.WRONG_PASSWORD -> UiRes.string.auth_failed_wrong_password
            failureReason == AuthFailureReason.EMAIL_NOT_VERIFIED -> UiRes.string.auth_blocked_email_not_verified
            failureReason == AuthFailureReason.DOCTOR_NOT_APPROVED -> UiRes.string.auth_blocked_doctor_not_approved
            else -> UiRes.string.auth_failed_sign_in
        }
        AuthEventType.LOGOUT -> UiRes.string.auth_signed_out
        AuthEventType.PASSWORD_RESET_REQUESTED -> UiRes.string.auth_reset_requested
        AuthEventType.PASSWORD_RESET_CODE_CHECK -> when {
            success -> UiRes.string.auth_reset_code_confirmed
            failureReason == AuthFailureReason.INVALID_RESET_CODE -> UiRes.string.auth_reset_code_wrong
            failureReason == AuthFailureReason.EXPIRED_RESET_CODE -> UiRes.string.auth_reset_code_expired
            failureReason == AuthFailureReason.TOO_MANY_ATTEMPTS -> UiRes.string.auth_reset_code_too_many
            else -> UiRes.string.auth_reset_code_failed
        }
        AuthEventType.PASSWORD_RESET ->
            if (success) UiRes.string.auth_password_changed else UiRes.string.auth_password_change_failed
        AuthEventType.UNKNOWN -> UiRes.string.account_activity
    }
}

/** "IP: 1.2.3.4" and a short device label, for the lines under the title */
@Composable
fun AuthEvent.detailLines(): List<String> = listOfNotNull(
    ipAddress?.let { stringResource(UiRes.string.auth_ip_address, it) },
    userAgent?.let { deviceLabel(UserAgentParser.parse(it)) }
)

@Composable
private fun deviceLabel(info: DeviceInfo): String = when (info) {
    DeviceInfo.App -> stringResource(UiRes.string.auth_device_app)
    is DeviceInfo.Browser ->
        if (info.os != null) stringResource(UiRes.string.auth_device_browser, info.browser, info.os)
        else info.browser
    is DeviceInfo.Other -> info.label
}
