package com.yehorsk.medical_platform_mobile.core.domain.push

// Where tapping a push notification should take the user.
sealed interface PushTarget {
    data class Chat(val conversationId: String) : PushTarget
    data class Appointment(val appointmentId: String) : PushTarget
    data object MedicalRecords : PushTarget
}

object PushPayload {

    const val KEY_TYPE = "type"
    const val KEY_CONVERSATION_ID = "conversationId"
    const val KEY_APPOINTMENT_ID = "appointmentId"
    const val KEY_MEDICAL_RECORD_ID = "medicalRecordId"

    private const val TYPE_NEW_MESSAGE = "new_message"
    private const val TYPE_ATTACHMENT_SCAN_FAILED = "attachment_scan_failed"
    private const val APPOINTMENT_TYPE_PREFIX = "appointment_"

    private val uuidRegex = Regex(
        "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
    )

    val keys = listOf(KEY_TYPE, KEY_CONVERSATION_ID, KEY_APPOINTMENT_ID, KEY_MEDICAL_RECORD_ID)

    /**
     * Maps the FCM `data` block to a navigation target. Returns null for unknown types or
     * malformed ids; the app then just opens normally. Ids are only format-checked here:
     * the screens load data through the authenticated API, which enforces access.
     */
    fun toTarget(data: Map<String, String?>): PushTarget? {
        val type = data[KEY_TYPE] ?: return null
        return when {
            type == TYPE_NEW_MESSAGE ->
                data[KEY_CONVERSATION_ID]?.takeIf(::isUuid)?.let(PushTarget::Chat)
            type == TYPE_ATTACHMENT_SCAN_FAILED -> PushTarget.MedicalRecords
            type.startsWith(APPOINTMENT_TYPE_PREFIX) ->
                data[KEY_APPOINTMENT_ID]?.takeIf(::isUuid)?.let(PushTarget::Appointment)
            else -> null
        }
    }

    /** Key that groups notifications about the same chat / appointment / record, mirroring the backend's collapse_key. */
    fun groupKey(data: Map<String, String?>): String? {
        data[KEY_CONVERSATION_ID]?.let { return it }
        data[KEY_APPOINTMENT_ID]?.let { return "appointment-$it" }
        data[KEY_MEDICAL_RECORD_ID]?.let { return "medical-record-$it" }
        return null
    }

    private fun isUuid(value: String): Boolean = uuidRegex.matches(value)

}
