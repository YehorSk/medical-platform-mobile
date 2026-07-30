package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel

sealed interface DashboardAction {

    data object OnNavigateToDoctorsScreen: DashboardAction

    data object OnNavigateToChatScreen: DashboardAction

    data object OnNavigateToAppointmentsScreen: DashboardAction

}