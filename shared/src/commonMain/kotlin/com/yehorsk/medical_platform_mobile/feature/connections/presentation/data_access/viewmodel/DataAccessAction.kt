package com.yehorsk.medical_platform_mobile.feature.connections.presentation.data_access.viewmodel

sealed interface DataAccessAction {
    data object OnGoBackClicked : DataAccessAction
}
