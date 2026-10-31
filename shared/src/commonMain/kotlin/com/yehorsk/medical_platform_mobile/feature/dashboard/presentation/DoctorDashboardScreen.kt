package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components.RecentConversationsBlock
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel.DashboardAction
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.ui.AppState
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.DashboardTopBar
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel.DashboardState
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel.DashboardViewModel
import com.yehorsk.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock


@Composable
fun DoctorDashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel(),
    navigateToChatScreen: () -> Unit = {},
    navigateToConversation: (String) -> Unit = {}
){

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    DoctorDashboardScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                DashboardAction.OnNavigateToChatScreen -> navigateToChatScreen()
                is DashboardAction.OnConversationClick -> navigateToConversation(action.conversationId)
                // Patient-only entries
                DashboardAction.OnNavigateToDoctorsScreen,
                DashboardAction.OnNavigateToAppointmentsScreen -> Unit
            }
        }
    )
}

@Composable
fun DoctorDashboardScreenRoot(
    modifier: Modifier = Modifier,
    state: DashboardState,
    onAction: (DashboardAction) -> Unit = {}
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DashboardTopBar(
            state = AppState(
                user = state.user,
                notificationCount = 23
            ),
            navigateToNotifications = {}
        )
        RecentConversationsBlock(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp),
            state = state,
            onSeeAllClick = {
                onAction(DashboardAction.OnNavigateToChatScreen)
            },
            onConversationClick = { id ->
                onAction(DashboardAction.OnConversationClick(id))
            }
        )
    }
}

@Preview
@Composable
fun DoctorDashboardScreenPreview(){
    AppTheme {
        DoctorDashboardScreenRoot(
            state = DashboardState(
                user = User(
                    id = "0",
                    email = "test@gmail.com",
                    firstName = "John",
                    lastName = "Doe",
                    role = "Patient",
                    title = "Bc.",
                    createdAt = Clock.System.now(),
                )
            )
        )
    }
}