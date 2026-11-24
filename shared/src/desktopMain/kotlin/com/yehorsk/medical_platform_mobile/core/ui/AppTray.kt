package com.yehorsk.medical_platform_mobile.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.rememberTrayState
import com.yehorsk.medical_platform_mobile.core.util.ActiveChatTracker
import com.yehorsk.medical_platform_mobile.feature.chat.domain.service.ChatConnectionClient
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.app_name
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

// Shows NOTIFICATION frames from the WebSocket as system notifications.
@Composable
fun ApplicationScope.AppTray(
    chatConnectionClient: ChatConnectionClient = koinInject()
) {
    val trayState = rememberTrayState()
    Tray(
        icon = painterResource(UiRes.drawable.stethoscope_24px),
        state = trayState,
        tooltip = stringResource(UiRes.string.app_name)
    )

    LaunchedEffect(Unit) {
        chatConnectionClient.notifications.collect { notification ->
            val conversationId = notification.data["conversationId"]
            if (conversationId != null && ActiveChatTracker.isChatActive(conversationId)) return@collect
            trayState.sendNotification(
                Notification(notification.title, notification.body, Notification.Type.Info)
            )
        }
    }
}
