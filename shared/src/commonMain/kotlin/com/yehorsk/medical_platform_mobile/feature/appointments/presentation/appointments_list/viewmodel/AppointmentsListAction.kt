package com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointments_list.viewmodel

sealed interface AppointmentsListAction {

    data object OnGoBackClicked: AppointmentsListAction

}