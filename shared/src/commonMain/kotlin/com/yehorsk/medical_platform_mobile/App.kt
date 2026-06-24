package com.yehorsk.medical_platform_mobile

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yehorsk.medical_platform_mobile.core.util.LocalSnackbarHostState
import com.yehorsk.medical_platform_mobile.core.util.ObserveAsEvents
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.toUiText
import com.yehorsk.medical_platform_mobile.navigation.Graph
import com.yehorsk.medical_platform_mobile.navigation.NavigationRoot
import com.yehorsk.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
@Preview
fun App() {

    val navController = rememberNavController()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch{
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = if(event.error != null) event.error.toUiText().asStringAsync() else event.message!!,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short,
                withDismissAction = true
            )
            if(result == SnackbarResult.ActionPerformed){
                event.action?.action?.invoke()
            }
        }
    }

    AppTheme {
        CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
            NavigationRoot(
                navController = navController,
                startDestination = Graph.Authentication
            )
        }
    }
}