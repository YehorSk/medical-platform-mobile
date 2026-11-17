package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel

import com.yehorsk.medical_platform_mobile.feature.connections.domain.models.response.AuditLog

data class DataAccessState(
    val logs: List<AuditLog> = emptyList(),
    val isLoading: Boolean = false,
    val hasMedicalCard: Boolean = true,
    val isConnected: Boolean = true
)
