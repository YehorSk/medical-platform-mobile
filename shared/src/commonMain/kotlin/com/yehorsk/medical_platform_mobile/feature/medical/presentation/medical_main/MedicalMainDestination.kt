package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_main

sealed interface MedicalMainDestination {

    data object MedicalRecords: MedicalMainDestination

    data object MedicalCard: MedicalMainDestination

    data object Back: MedicalMainDestination

}