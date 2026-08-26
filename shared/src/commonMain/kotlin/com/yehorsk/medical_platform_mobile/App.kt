package com.yehorsk.medical_platform_mobile

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.util.AuthEvent
import com.yehorsk.medical_platform_mobile.core.util.AuthEventManager
import com.yehorsk.medical_platform_mobile.core.util.LocalSnackbarHostState
import com.yehorsk.medical_platform_mobile.core.util.ObserveAsEvents
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.toUiText
import com.yehorsk.medical_platform_mobile.navigation.Graph
import com.yehorsk.medical_platform_mobile.navigation.NavigationRoot
import com.yehorsk.theme.AppTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    viewModel: MainViewModel = koinViewModel(),
    authEventManager: AuthEventManager = koinInject()
) {

    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    ObserveAsEvents(authEventManager.authEvents){event ->
        when(event) {
            AuthEvent.NavigateToLogin -> {
                navController.navigate(Graph.Authentication) {
                    popUpTo(Graph.Authentication) {
                        inclusive = false
                    }
                }
            }
        }
    }

    ObserveAsEvents(viewModel.events){event ->
        when(event) {
            is MainEvent.OnSessionExpired -> {
                navController.navigate(Graph.Authentication) {
                    popUpTo(Graph.Authentication) {
                        inclusive = false
                    }
                }
            }
        }
    }

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
        if (!state.isCheckingAuth && state.userRole != null) {
            CompositionLocalProvider(
                LocalUserRole provides state.userRole!!,
                LocalSnackbarHostState provides snackbarHostState
            ) {
                NavigationRoot(
                    navController = navController,
                    startDestination = when (state.userRole) {
                        UserRole.PATIENT -> Graph.Patient
                        UserRole.DOCTOR -> Graph.Doctor
                        UserRole.ADMIN -> Graph.Authentication
                        else -> Graph.Authentication
                    }
                )
            }
        } else if (!state.isCheckingAuth) {
            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState
            ) {
                NavigationRoot(
                    navController = navController,
                    startDestination = Graph.Authentication
                )
            }
        }
    }
}

val LocalUserRole = compositionLocalOf<UserRole> {
    error("UserRole not provided")
}