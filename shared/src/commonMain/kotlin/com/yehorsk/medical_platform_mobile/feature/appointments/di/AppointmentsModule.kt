package com.yehorsk.medical_platform_mobile.feature.appointments.di

import com.yehorsk.medical_platform_mobile.feature.appointments.data.AppointmentServiceImpl
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.AppointmentService
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.ScheduleService
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.book_appointment.viewmodel.BookAppointmentViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appointmentModule = module {
    viewModelOf(::BookAppointmentViewModel)
    singleOf(::AppointmentServiceImpl) bind AppointmentService::class
    singleOf(::AppointmentServiceImpl) bind ScheduleService::class
}