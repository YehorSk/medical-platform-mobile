package com.yehorsk.medical_platform_mobile.feature.appointments.di

import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appointmentModule = module {
    viewModelOf(::BookAppointmentViewModel)
}