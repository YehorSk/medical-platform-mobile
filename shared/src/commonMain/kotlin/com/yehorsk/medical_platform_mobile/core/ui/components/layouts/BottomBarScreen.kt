package com.yehorsk.medical_platform_mobile.core.ui.components.layouts

import com.yehorsk.medical_platform_mobile.navigation.Screen
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.connect
import medicalplatformmobile.shared.generated.resources.chat_24px
import medicalplatformmobile.shared.generated.resources.home
import medicalplatformmobile.shared.generated.resources.home_24px
import medicalplatformmobile.shared.generated.resources.chat
import medicalplatformmobile.shared.generated.resources.group_24px
import medicalplatformmobile.shared.generated.resources.health
import medicalplatformmobile.shared.generated.resources.health_and_safety_24px
import medicalplatformmobile.shared.generated.resources.settings
import medicalplatformmobile.shared.generated.resources.settings_24px
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


sealed class BottomBarScreen(
    val screen: Screen,
    val title: StringResource,
    val icon: DrawableResource
) {
   object Home : BottomBarScreen(
       screen = Screen.Home,
       title = UiRes.string.home,
       icon = UiRes.drawable.home_24px
   )

    object Chat : BottomBarScreen(
        screen = Screen.Chat,
        title = UiRes.string.chat,
        icon = UiRes.drawable.chat_24px
    )

    object Health : BottomBarScreen(
        screen = Screen.Health,
        title = UiRes.string.health,
        icon = UiRes.drawable.health_and_safety_24px
    )

    object Connect : BottomBarScreen(
        screen = Screen.Connect,
        title = UiRes.string.connect,
        icon = UiRes.drawable.group_24px
    )

    object Profile : BottomBarScreen(
        screen = Screen.Settings,
        title = UiRes.string.settings,
        icon = UiRes.drawable.settings_24px
    )
}