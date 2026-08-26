package com.yehorsk.medical_platform_mobile.feature.connections.presentation.main.navigation

sealed interface ConnectionsMainDestination {
    data object Back : ConnectionsMainDestination
    data object Appointments : ConnectionsMainDestination
    data object FindDoctor : ConnectionsMainDestination
    data object MyDoctors : ConnectionsMainDestination
    data object MyPatients : ConnectionsMainDestination
    data object PendingRequests : ConnectionsMainDestination
    data object DataAccess : ConnectionsMainDestination
}