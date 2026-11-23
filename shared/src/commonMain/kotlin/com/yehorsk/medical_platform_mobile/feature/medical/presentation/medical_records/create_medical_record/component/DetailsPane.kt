package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.textfields.DefaultMultilineTextField
import com.yehorsk.medical_platform_mobile.core.ui.components.textfields.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.component.AppointmentPatientCard
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordAction
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordScreenState
import com.yehorsk.medical_platform_mobile.util.UiText
import com.yehorsk.medical_platform_mobile.feature.medical.data.mappers.MedicalAttachmentTypes
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.record_add_files
import medicalplatformmobile.shared.generated.resources.record_attachments
import medicalplatformmobile.shared.generated.resources.record_clear_files
import medicalplatformmobile.shared.generated.resources.record_remove_file
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
                    minLines = 3,
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
                    minLines = 3,
                    placeholder = stringResource(UiRes.string.record_recommendations_placeholder),
                    onValueChange = { onAction(CreateRecordAction.OnRecommendationsUpdated(it)) }
                )
            }
        )
        MultiFilePicker(
            modifier = Modifier.padding(top = 12.dp),
            files = state.files,
            onFilesPicked = { onAction(CreateRecordAction.FilesPicked(it)) },
            onFileRemoved = { onAction(CreateRecordAction.FileRemoved(it)) },
            label = stringResource(UiRes.string.record_attachments),
            buttonText = stringResource(UiRes.string.record_add_files),
            removeText = stringResource(UiRes.string.record_remove_file),
            extensions = MedicalAttachmentTypes.extensions,
            canAddMore = state.canAddMore,
            enabled = !state.isSubmitting,
        )
        if (state.files.isNotEmpty()) {
            DefaultButton(
                modifier = Modifier.padding(top = 12.dp),
                onClick = { onAction(CreateRecordAction.ClearFiles) },
                enabled = !state.isSubmitting,
                text = stringResource(UiRes.string.record_clear_files)
            )
        }
        DefaultButton(
            modifier = Modifier.padding(vertical = 12.dp),
            text = stringResource(UiRes.string.record_save),
            loading = state.isSubmitting,
            enabled = state.canSubmit,
            onClick = { onAction(CreateRecordAction.OnCreateMedicalRecordClicked) }
        )
    }
}