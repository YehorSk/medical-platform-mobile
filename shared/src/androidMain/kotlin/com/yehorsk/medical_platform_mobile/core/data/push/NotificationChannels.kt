package com.yehorsk.medical_platform_mobile.core.data.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.getSystemService
import com.yehorsk.medical_platform_mobile.shared.R

// Channel ids must match android.notification.channel_id sent by the backend.
object NotificationChannels {

    const val MESSAGES = "messages"
    const val APPOINTMENTS = "appointments"
    const val MEDICAL_RECORDS = "medical_records"

    // Created at app start so they exist before the system shows a background push.
    fun createAll(context: Context) {
        val manager = context.getSystemService<NotificationManager>() ?: return
        manager.createNotificationChannels(
            listOf(
                channel(context, MESSAGES, R.string.notification_channel_messages, R.string.notification_channel_messages_description),
                channel(context, APPOINTMENTS, R.string.notification_channel_appointments, R.string.notification_channel_appointments_description),
                channel(context, MEDICAL_RECORDS, R.string.notification_channel_medical_records, R.string.notification_channel_medical_records_description)
            )
        )
    }

    fun forType(type: String?): String = when {
        type == "new_message" -> MESSAGES
        type == "attachment_scan_failed" -> MEDICAL_RECORDS
        else -> APPOINTMENTS
    }

    private fun channel(context: Context, id: String, name: Int, description: Int) =
        NotificationChannel(id, context.getString(name), NotificationManager.IMPORTANCE_HIGH).apply {
            this.description = context.getString(description)
            lockscreenVisibility = android.app.Notification.VISIBILITY_PRIVATE
        }

}
