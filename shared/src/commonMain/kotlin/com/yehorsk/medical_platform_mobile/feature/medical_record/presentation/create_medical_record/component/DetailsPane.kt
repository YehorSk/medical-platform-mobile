package com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Appointment
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component.AppointmentPatientCard
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel.CreateRecordAction
import com.yehorsk.medical_platform_mobile.feature.medical_record.presentation.create_medical_record.viewmodel.CreateRecordScreenState

@Composable
fun DetailsPane(
    state: CreateRecordScreenState,
    onAction: (CreateRecordAction) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        state.appointment?.let {
            AppointmentPatientCard(
                modifier = Modifier.padding(vertical = 12.dp),
                appointment = state.appointment
            )
        }
        ChooseMedicalRecordType(
            modifier = Modifier.padding(vertical = 12.dp),
            selectedType = state.form.medicalRecordType,
            onSelectRecord = { onAction(CreateRecordAction.OnMedRecordTypeSelected(it)) }
        )
    }
}