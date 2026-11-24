package com.yehorsk.medical_platform_mobile.core.data.push

import android.content.Intent
import com.yehorsk.medical_platform_mobile.core.domain.push.PushPayload
import org.koin.java.KoinJavaComponent.get

/**
 * Reads the FCM `data` block from the intent that opened the activity (a tapped notification,
 * shown either by the system in the background or by [AppFirebaseMessagingService] in the foreground)
 * and hands the target to [PendingPushNavigation].
 */
object PushIntentHandler {

    fun handle(intent: Intent?) {
        val extras = intent?.extras ?: return
        val data = PushPayload.keys.associateWith { extras.getString(it) }
        val target = PushPayload.toTarget(data) ?: return
        get<PendingPushNavigation>(PendingPushNavigation::class.java).set(target)
        // Don't navigate again if the same intent is delivered once more.
        PushPayload.keys.forEach(intent::removeExtra)
    }

}
