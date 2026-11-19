package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access_details.viewmodel

sealed interface DataAccessDetailsAction {
    data object OnGoBackClicked : DataAccessDetailsAction
    data class OnManageAccessClicked(val doctorId: String) : DataAccessDetailsAction
}
