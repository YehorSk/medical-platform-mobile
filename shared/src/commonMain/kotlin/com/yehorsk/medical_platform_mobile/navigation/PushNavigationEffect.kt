package com.yehorsk.medical_platform_mobile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yehorsk.medical_platform_mobile.core.data.push.PendingPushNavigation
import com.yehorsk.medical_platform_mobile.core.domain.push.PushTarget
import org.koin.compose.koinInject

/**
 * Opens the screen for a tapped notification once the user is inside their own
 * (patient or doctor) graph, i.e. after login and the PIN screen. The graph comes from
 * the session, never from the push payload. Without a session the target is dropped.
 */
@Composable
fun PushNavigationEffect(
    navController: NavHostController,
    isCheckingAuth: Boolean,
    isLoggedIn: Boolean,
    pendingPushNavigation: PendingPushNavigation = koinInject()
) {
    val target by pendingPushNavigation.target.collectAsStateWithLifecycle()
    val currentEntry by navController.currentBackStackEntryAsState()
    val isInMainGraph = currentEntry?.destination?.hierarchy?.any {
        it.hasRoute(Graph.Patient::class) || it.hasRoute(Graph.Doctor::class)
    } == true

    LaunchedEffect(target, isInMainGraph, isCheckingAuth, isLoggedIn) {
        val pending = target ?: return@LaunchedEffect
        when {
            !isCheckingAuth && !isLoggedIn -> pendingPushNavigation.consume()
            isInMainGraph -> {
                pendingPushNavigation.consume()
                navController.navigate(pending.toScreen()) {
                    launchSingleTop = true
                }
            }
        }
    }
}

private fun PushTarget.toScreen(): Screen = when (this) {
    is PushTarget.Chat -> Screen.ChatDetails(conversationId)
    is PushTarget.Appointment -> Screen.AppointmentDetails(appointmentId)
    PushTarget.MedicalRecords -> Screen.Health
}
