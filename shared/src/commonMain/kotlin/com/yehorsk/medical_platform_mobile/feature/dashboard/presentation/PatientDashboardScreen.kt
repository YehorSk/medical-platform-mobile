package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.ui.AppState
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.theme.AppTheme

@Composable
fun PatientDashboardScreen(
    modifier: Modifier = Modifier
){
    PatientDashboardScreenRoot(
        modifier = modifier
    )
}

@Composable
fun PatientDashboardScreenRoot(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        AppTopBar(
            state = AppState(
                user = User(
                    id = 0,
                    email = "test@gmail.com",
                    firstName = "John",
                    lastName = "Doe",
                    role = "patient"
                ),
                notificationCount = 23
            ),
            navigateToNotifications = {}
        )
    }
}

@Preview
@Composable
fun PatientDashboardScreenPreview(){
    AppTheme {
        PatientDashboardScreenRoot()
    }
}