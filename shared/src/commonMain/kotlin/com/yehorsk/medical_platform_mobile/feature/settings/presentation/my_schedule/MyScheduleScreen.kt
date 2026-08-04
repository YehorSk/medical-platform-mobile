package com.yehorsk.medical_platform_mobile.feature.settings.presentation.my_schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.component.SettingsMainHeader
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.update_password.viewmodel.UpdatePasswordAction

@Composable
fun MyScheduleScreen(
    modifier: Modifier = Modifier
){
    MyScheduleScreenRoot(
        modifier = modifier
    )
}

@Composable
fun MyScheduleScreenRoot(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SettingsMainHeader(
            showGoBackButton = true,
            showUserData = false,
            onGoBackButtonClicked = {

            }
        )
    }
}