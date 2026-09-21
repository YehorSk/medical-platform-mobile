package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultMultilineTextField
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component.AppointmentPatientCard
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordAction
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordScreenState
import com.yehorsk.medical_platform_mobile.util.UiText
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.record_diagnosis
import medicalplatformmobile.shared.generated.resources.record_diagnosis_placeholder
import medicalplatformmobile.shared.generated.resources.record_recommendations
import medicalplatformmobile.shared.generated.resources.record_recommendations_placeholder
import medicalplatformmobile.shared.generated.resources.record_save
import medicalplatformmobile.shared.generated.resources.record_title
import medicalplatformmobile.shared.generated.resources.record_title_placeholder
import medicalplatformmobile.shared.generated.resources.record_type
import org.jetbrains.compose.resources.stringResource

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
                modifier = Modifier.padding(top = 12.dp),
                appointment = state.appointment
            )
        }
        CreateRecordScreenComponent(
            modifier = Modifier.padding(top = 12.dp),
            title = UiText.Resource(UiRes.string.record_type),
            content = {
                ChooseMedicalRecordType(
                    selectedType = state.form.medicalRecordType,
                    onSelectRecord = { onAction(CreateRecordAction.OnMedRecordTypeSelected(it)) }
                )
            }
        )
        CreateRecordScreenComponent(
            modifier = Modifier.padding(top = 12.dp),
            title = UiText.Resource(UiRes.string.record_title),
            content = {
                DefaultTextField(
                    value = state.form.title,
                    placeholder = stringResource(UiRes.string.record_title_placeholder),
                    onValueChange = { onAction(CreateRecordAction.OnTitleUpdated(it)) }
                )
            }
        )
        CreateRecordScreenComponent(
            modifier = Modifier.padding(top = 12.dp),
            title = UiText.Resource(UiRes.string.record_diagnosis),
            content = {
                DefaultMultilineTextField(
                    value = state.form.diagnosis,
                    placeholder = stringResource(UiRes.string.record_diagnosis_placeholder),
                    onValueChange = { onAction(CreateRecordAction.OnDiagnosisUpdated(it)) }
                )
            }
        )
        CreateRecordScreenComponent(
            modifier = Modifier.padding(top = 12.dp),
            title = UiText.Resource(UiRes.string.record_recommendations),
            content = {
                DefaultMultilineTextField(
                    value = state.form.recommendations,
                    placeholder = stringResource(UiRes.string.record_recommendations_placeholder),
                    onValueChange = { onAction(CreateRecordAction.OnRecommendationsUpdated(it)) }
                )
            }
        )
        DefaultButton(
            modifier = Modifier.padding(vertical = 12.dp),
            text = stringResource(UiRes.string.record_save),
            onClick = {}
        )
    }
}