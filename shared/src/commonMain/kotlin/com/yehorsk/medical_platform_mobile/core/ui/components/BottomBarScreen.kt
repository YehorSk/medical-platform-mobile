package com.yehorsk.medical_platform_mobile.core.ui.components

import com.yehorsk.medical_platform_mobile.navigation.Screen
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.calendar
import medicalplatformmobile.shared.generated.resources.calendar_today_24px
import medicalplatformmobile.shared.generated.resources.chat_24px
import medicalplatformmobile.shared.generated.resources.dashboard
import medicalplatformmobile.shared.generated.resources.home_24px
import medicalplatformmobile.shared.generated.resources.messages
import medicalplatformmobile.shared.generated.resources.settings
import medicalplatformmobile.shared.generated.resources.settings_24px
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


sealed class BottomBarScreen(
    val screen: Screen,
    val title: StringResource,
    val icon: DrawableResource
) {
   object Dashboard : BottomBarScreen(
       screen = Screen.Dashboard,
       title = UiRes.string.dashboard,
       icon = UiRes.drawable.home_24px
   )

    object Calendar : BottomBarScreen(
        screen = Screen.Calendar,
        title = UiRes.string.calendar,
        icon = UiRes.drawable.calendar_today_24px
    )

    object Messages : BottomBarScreen(
        screen = Screen.Messages,
        title = UiRes.string.messages,
        icon = UiRes.drawable.chat_24px
    )

    object Profile : BottomBarScreen(
        screen = Screen.Settings,
        title = UiRes.string.settings,
        icon = UiRes.drawable.settings_24px
    )
}