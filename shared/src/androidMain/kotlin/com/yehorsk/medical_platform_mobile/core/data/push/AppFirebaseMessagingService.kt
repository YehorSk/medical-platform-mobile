package com.yehorsk.medical_platform_mobile.core.data.push

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yehorsk.medical_platform_mobile.core.domain.push.PushPayload
import com.yehorsk.medical_platform_mobile.core.util.ActiveChatTracker
import com.yehorsk.medical_platform_mobile.shared.R
import org.koin.android.ext.android.inject

class AppFirebaseMessagingService : FirebaseMessagingService() {

    private val pushTokenManager: PushTokenManager by inject()

    override fun onNewToken(token: String) {
        pushTokenManager.onNewToken(token)
    }

    @SuppressLint("MissingPermission")
    override fun onMessageReceived(message: RemoteMessage) {
        val notification = message.notification ?: return
        val notificationManager = NotificationManagerCompat.from(this)
        if (!notificationManager.areNotificationsEnabled()) return

        val data = message.data
        // The user is looking at this chat; the message already arrives over the WebSocket.
        val conversationId = data[PushPayload.KEY_CONVERSATION_ID]
        if (conversationId != null && ActiveChatTracker.isChatActive(conversationId)) return

        val groupKey = PushPayload.groupKey(data)

        val publicVersion = NotificationCompat.Builder(this, NotificationChannels.forType(data[PushPayload.KEY_TYPE]))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_public_title))
            .build()

        val built = NotificationCompat.Builder(this, NotificationChannels.forType(data[PushPayload.KEY_TYPE]))
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setPublicVersion(publicVersion)
            .setContentIntent(contentIntent(data, groupKey))
            .build()

        notificationManager.notify(groupKey, NOTIFICATION_ID, built)
    }

    private fun contentIntent(data: Map<String, String>, groupKey: String?): PendingIntent? {
        val intent = packageManager.getLaunchIntentForPackage(packageName) ?: return null
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        PushPayload.keys.forEach { key -> data[key]?.let { intent.putExtra(key, it) } }
        return PendingIntent.getActivity(
            this,
            (groupKey ?: "").hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private companion object {
        const val NOTIFICATION_ID = 1
    }

}
