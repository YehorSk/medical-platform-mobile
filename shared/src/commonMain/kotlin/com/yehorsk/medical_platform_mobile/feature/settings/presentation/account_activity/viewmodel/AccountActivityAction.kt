package com.yehorsk.medical_platform_mobile.feature.settings.presentation.account_activity.viewmodel

sealed interface AccountActivityAction {
    data object OnGoBackClicked : AccountActivityAction
}
