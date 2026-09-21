package com.yehorsk.medical_platform_mobile.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yehorsk.medical_platform_mobile.core.domain.model.AccessStatus
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.domain.model.WeekDay
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.AppointmentStatus
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.BloodType
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Gender
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookingStep
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType
import com.yehorsk.theme.LocalExtendedColors
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.blood_type_a_negative
import medicalplatformmobile.shared.generated.resources.blood_type_a_positive
import medicalplatformmobile.shared.generated.resources.blood_type_ab_negative
import medicalplatformmobile.shared.generated.resources.blood_type_ab_positive
import medicalplatformmobile.shared.generated.resources.blood_type_b_negative
import medicalplatformmobile.shared.generated.resources.blood_type_b_positive
import medicalplatformmobile.shared.generated.resources.blood_type_o_negative
import medicalplatformmobile.shared.generated.resources.blood_type_o_positive
import medicalplatformmobile.shared.generated.resources.confirm
import medicalplatformmobile.shared.generated.resources.d_ago
import medicalplatformmobile.shared.generated.resources.date
import medicalplatformmobile.shared.generated.resources.doctor
import medicalplatformmobile.shared.generated.resources.friday_short
import medicalplatformmobile.shared.generated.resources.gender_female
import medicalplatformmobile.shared.generated.resources.gender_male
import medicalplatformmobile.shared.generated.resources.h_ago
import medicalplatformmobile.shared.generated.resources.just_now
import medicalplatformmobile.shared.generated.resources.m_ago
import medicalplatformmobile.shared.generated.resources.monday_short
import medicalplatformmobile.shared.generated.resources.not_available
import medicalplatformmobile.shared.generated.resources.record_type_clinical_note
import medicalplatformmobile.shared.generated.resources.record_type_diagnosis
import medicalplatformmobile.shared.generated.resources.record_type_lab_result
import medicalplatformmobile.shared.generated.resources.record_type_prescription
import medicalplatformmobile.shared.generated.resources.record_type_procedure
import medicalplatformmobile.shared.generated.resources.record_type_vaccination
import medicalplatformmobile.shared.generated.resources.record_type_visit
import medicalplatformmobile.shared.generated.resources.saturday_short
import medicalplatformmobile.shared.generated.resources.sunday_short
import medicalplatformmobile.shared.generated.resources.thursday_short
import medicalplatformmobile.shared.generated.resources.time
import medicalplatformmobile.shared.generated.resources.tuesday_short
import medicalplatformmobile.shared.generated.resources.wednesday_short
import org.jetbrains.compose.resources.stringResource
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun BloodType.toDisplayName(): UiText {
    return when (this) {
        BloodType.A_POSITIVE ->
            UiText.Resource(UiRes.string.blood_type_a_positive)

        BloodType.A_NEGATIVE ->
            UiText.Resource(UiRes.string.blood_type_a_negative)

        BloodType.B_POSITIVE ->
            UiText.Resource(UiRes.string.blood_type_b_positive)

        BloodType.B_NEGATIVE ->
            UiText.Resource(UiRes.string.blood_type_b_negative)

        BloodType.AB_POSITIVE ->
            UiText.Resource(UiRes.string.blood_type_ab_positive)

        BloodType.AB_NEGATIVE ->
            UiText.Resource(UiRes.string.blood_type_ab_negative)

        BloodType.O_POSITIVE ->
            UiText.Resource(UiRes.string.blood_type_o_positive)

        BloodType.O_NEGATIVE ->
            UiText.Resource(UiRes.string.blood_type_o_negative)

        BloodType.UNKNOWN ->
            UiText.Resource(UiRes.string.not_available)
    }
}

fun Gender.toDisplayName(): UiText {
    return when (this) {
        Gender.MALE ->
            UiText.Resource(UiRes.string.gender_male)
        Gender.FEMALE ->
            UiText.Resource(UiRes.string.gender_female)
        Gender.UNKNOWN ->
            UiText.Resource(UiRes.string.not_available)
    }
}

fun MedicalRecordType.toDisplayName(): UiText {
    return when (this) {
        MedicalRecordType.VISIT ->
            UiText.Resource(UiRes.string.record_type_visit)
        MedicalRecordType.LAB_RESULT ->
            UiText.Resource(UiRes.string.record_type_lab_result)
        MedicalRecordType.PRESCRIPTION ->
            UiText.Resource(UiRes.string.record_type_prescription)
        MedicalRecordType.VACCINATION ->
            UiText.Resource(UiRes.string.record_type_vaccination)
        MedicalRecordType.PROCEDURE ->
            UiText.Resource(UiRes.string.record_type_procedure)
        MedicalRecordType.DIAGNOSIS ->
            UiText.Resource(UiRes.string.record_type_diagnosis)
        MedicalRecordType.CLINICAL_NOTE ->
            UiText.Resource(UiRes.string.record_type_clinical_note)
    }
}

fun calculateAge(dateOfBirth: String): Int? {
    if (dateOfBirth.isBlank()) return null

    return runCatching {
        val birthDate = java.time.LocalDate.parse(dateOfBirth)
        val today = java.time.LocalDate.now()

        java.time.Period
            .between(birthDate, today)
            .years
            .coerceAtLeast(0)
    }.getOrNull()
}

fun getRole(role: String): UserRole {
    return UserRole.entries.find { it.name.equals(role, ignoreCase = true) } ?: UserRole.PATIENT
}

fun getAccessStatus(status: String): AccessStatus {
    return AccessStatus.entries.find { it.name.equals(status, ignoreCase = true) } ?: AccessStatus.UNKNOWN
}

fun getAppointmentStatus(status: String): AppointmentStatus {
    return AppointmentStatus.entries.find { it.name.equals(status, ignoreCase = true) } ?: AppointmentStatus.UNKNOWN
}

fun getGender(status: String): Gender {
    return Gender.entries.find { it.name.equals(status, ignoreCase = true) } ?: Gender.UNKNOWN
}

fun getBloodType(status: String): BloodType {
    return BloodType.entries.find { it.name.equals(status, ignoreCase = true) } ?: BloodType.UNKNOWN
}

fun getWeekDay(status: String): WeekDay {
    return WeekDay.entries.find { it.name.equals(status, ignoreCase = true) } ?: WeekDay.UNKNOWN
}

private val apiFormatter = DateTimeFormatter.ISO_LOCAL_TIME
private val uiFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun String.toDisplayTime(): String =
    LocalTime.parse(this, apiFormatter).format(uiFormatter)

@Composable
fun AppointmentStatus.toColor(): Color {
    val colors = LocalExtendedColors.current
    return when (this) {
        AppointmentStatus.PENDING -> colors.statusPending
        AppointmentStatus.CONFIRMED -> colors.statusConfirmed
        AppointmentStatus.REJECTED -> colors.statusRejected
        AppointmentStatus.CANCELLED -> colors.statusCancelled
        AppointmentStatus.COMPLETED -> colors.statusCompleted
        else -> colors.statusCancelled
    }
}

sealed class TimeAgo {
    data object JustNow : TimeAgo()
    data class Minutes(val value: Long) : TimeAgo()
    data class Hours(val value: Long) : TimeAgo()
    data class Days(val value: Long) : TimeAgo()
    data class Date(val value: String) : TimeAgo()
}

fun formatTimeAgo(createdAt: String): TimeAgo {
    val instant = Instant.parse(createdAt)
    val diff = Clock.System.now().toEpochMilliseconds() - instant.toEpochMilliseconds()

    val minutes = diff / 1000 / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        minutes < 1 -> TimeAgo.JustNow
        minutes < 60 -> TimeAgo.Minutes(minutes)
        hours < 24 -> TimeAgo.Hours(hours)
        days < 7 -> TimeAgo.Days(days)
        else -> {
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            TimeAgo.Date("${dt.day}.${dt.month.number.toString().padStart(2, '0')}.${dt.year}")
        }
    }
}

@Composable
fun TimeAgo.toText(): String {
    return when (this) {
        is TimeAgo.JustNow -> stringResource(UiRes.string.just_now)
        is TimeAgo.Minutes -> stringResource(UiRes.string.m_ago, value)
        is TimeAgo.Hours -> stringResource(UiRes.string.h_ago, value)
        is TimeAgo.Days -> stringResource(UiRes.string.d_ago, value)
        is TimeAgo.Date -> value
    }
}

@Composable
fun BookingStep.titleRes(): String = when (this) {
    BookingStep.Doctor -> stringResource(UiRes.string.doctor)
    BookingStep.Date -> stringResource(UiRes.string.date)
    BookingStep.Time -> stringResource(UiRes.string.time)
    BookingStep.Confirm -> stringResource(UiRes.string.confirm)
}

@Composable
fun WeekDay.shortName(): String = when (this) {
    WeekDay.MONDAY -> stringResource(UiRes.string.monday_short)
    WeekDay.TUESDAY -> stringResource(UiRes.string.tuesday_short)
    WeekDay.WEDNESDAY -> stringResource(UiRes.string.wednesday_short)
    WeekDay.THURSDAY -> stringResource(UiRes.string.thursday_short)
    WeekDay.FRIDAY -> stringResource(UiRes.string.friday_short)
    WeekDay.SATURDAY -> stringResource(UiRes.string.saturday_short)
    WeekDay.SUNDAY -> stringResource(UiRes.string.sunday_short)
    WeekDay.UNKNOWN -> "-"
}

fun extractDate(datetime: String): String {
    val instant = Instant.parse(datetime)
    val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dt.day.toString().padStart(2, '0')}.${dt.month.number.toString().padStart(2, '0')}.${dt.year}"
}

fun extractTime(datetime: String): String {
    val instant = Instant.parse(datetime)
    val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${dt.hour.toString().padStart(2, '0')}:${dt.minute.toString().padStart(2, '0')}"
}