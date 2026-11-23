package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.medical_platform_mobile.core.data.network.ConnectivityObserver
import com.yehorsk.medical_platform_mobile.core.domain.logging.MainLogger
import com.yehorsk.medical_platform_mobile.core.util.SnackbarController
import com.yehorsk.medical_platform_mobile.core.util.SnackbarEvent
import com.yehorsk.medical_platform_mobile.core.util.onFailure
import com.yehorsk.medical_platform_mobile.core.util.onSuccess
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.AppointmentService
import com.yehorsk.medical_platform_mobile.feature.auth.presentation.login.viewmodel.LoginEvent
import com.yehorsk.medical_platform_mobile.core.util.DataError
import com.yehorsk.medical_platform_mobile.feature.medical.data.mappers.MedicalAttachmentTypes
import com.yehorsk.medical_platform_mobile.feature.medical.data.mappers.toUploadFile
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.AttachmentStatus
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyHitRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyPartSelection
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.BodyRegion
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.NewMedicalRecord
import com.yehorsk.medical_platform_mobile.feature.medical.domain.service.MedicalRecordService
import com.yehorsk.medical_platform_mobile.util.UiText
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.size
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.record_created_with_failed_files
import medicalplatformmobile.shared.generated.resources.record_error_already_exists
import medicalplatformmobile.shared.generated.resources.record_error_appointment_missing
import medicalplatformmobile.shared.generated.resources.record_error_appointment_not_found
import medicalplatformmobile.shared.generated.resources.record_error_body_part_required
import medicalplatformmobile.shared.generated.resources.record_error_diagnosis_required
import medicalplatformmobile.shared.generated.resources.record_error_file_too_large
import medicalplatformmobile.shared.generated.resources.record_error_file_type
import medicalplatformmobile.shared.generated.resources.record_error_files_total_too_large
import medicalplatformmobile.shared.generated.resources.record_error_title_required
import medicalplatformmobile.shared.generated.resources.record_error_too_many_files
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateMedicalRecordViewModel(
    private val mainLogger: MainLogger,
    private val connectivityObserver: ConnectivityObserver,
    private val appointmentService: AppointmentService,
    private val medicalRecordService: MedicalRecordService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val eventChannel = Channel<CreateRecordEvent>()
    val events = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val appointmentId = savedStateHandle.get<String>("appointmentId")
        ?: throw IllegalStateException("Appointment id is missing")

    private val _uiState = MutableStateFlow(CreateRecordScreenState())
    val uiState = _uiState
        .onStart {
            if(!hasLoadedInitialData){
                getAppointment()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateRecordScreenState()
        )

    init {
        observeConnectivity()
    }

    fun onAction(action: CreateRecordAction) {
        when (action) {
            is CreateRecordAction.OnBodyPartSelected -> onBodyPartSelected(action.part)
            is CreateRecordAction.OnBodyRegionSelected -> onBodyRegionSelected(action.region)
            is CreateRecordAction.OnTabSelected -> onTabSelected(action.tab)
            is CreateRecordAction.OnMedRecordTypeSelected -> onMedRecordSelected(action.type)
            is CreateRecordAction.OnDiagnosisUpdated -> onDiagnosisUpdated(action.diagnosis)
            is CreateRecordAction.OnRecommendationsUpdated -> onRecommendationsUpdated(action.recommendations)
            is CreateRecordAction.OnTitleUpdated -> onTitleUpdated(action.title)
            CreateRecordAction.OnCreateMedicalRecordClicked -> createMedicalRecord()
            CreateRecordAction.OnGoBackClicked -> {}
            is CreateRecordAction.FilesPicked -> onFilesPicked(action.files)

            is CreateRecordAction.FileRemoved -> _uiState.update {
                if (it.isSubmitting) it else it.copy(files = it.files - action.file)
            }

            CreateRecordAction.ClearFiles -> _uiState.update {
                if (it.isSubmitting) it else it.copy(files = emptyList())
            }

        }
    }

    private fun onFilesPicked(picked: List<PlatformFile>) {
        if (_uiState.value.isSubmitting) return
        val rejected = mutableListOf<UiText>()
        val accepted = picked.filter { file ->
            when {
                MedicalAttachmentTypes.contentTypeOf(file.extension) == null -> {
                    rejected += UiText.Resource(UiRes.string.record_error_file_type, arrayOf<Any>(file.name))
                    false
                }
                file.size() > CreateRecordScreenState.MAX_FILE_BYTES -> {
                    rejected += UiText.Resource(UiRes.string.record_error_file_too_large, arrayOf<Any>(file.name))
                    false
                }
                else -> true
            }
        }
        val current = _uiState.value
        val merged = (current.files + accepted).distinctBy { it.path }
        if (merged.size > current.maxFiles) {
            rejected += UiText.Resource(UiRes.string.record_error_too_many_files, arrayOf<Any>(current.maxFiles))
        }
        _uiState.update { it.copy(files = merged.take(it.maxFiles)) }
        rejected.firstOrNull()?.let { showMessage(it) }
    }

    // Returns null when the form is valid
    private fun validate(state: CreateRecordScreenState): UiText? {
        val form = state.form
        val totalBytes = state.files.sumOf { it.size() }
        return when {
            form.appointmentId.isBlank() || form.patientId.isBlank() ->
                UiText.Resource(UiRes.string.record_error_appointment_missing)
            form.title.isBlank() -> UiText.Resource(UiRes.string.record_error_title_required)
            form.diagnosis.isBlank() -> UiText.Resource(UiRes.string.record_error_diagnosis_required)
            form.selectedBodyParts.isEmpty() -> UiText.Resource(UiRes.string.record_error_body_part_required)
            state.files.size > state.maxFiles ->
                UiText.Resource(UiRes.string.record_error_too_many_files, arrayOf<Any>(state.maxFiles))
            totalBytes > CreateRecordScreenState.MAX_TOTAL_BYTES ->
                UiText.Resource(UiRes.string.record_error_files_total_too_large)
            else -> null
        }
    }

    private fun createMedicalRecord() {
        val state = _uiState.value
        // A second create for the same appointment fails on the server, so never send twice
        if (state.isSubmitting) return
        validate(state)?.let {
            showMessage(it)
            return
        }
        val uploads = state.files.map { file ->
            file.toUploadFile() ?: run {
                showMessage(UiText.Resource(UiRes.string.record_error_file_type, arrayOf<Any>(file.name)))
                return
            }
        }
        val record = state.form.toNewMedicalRecord()

        _uiState.update { it.copy(isSubmitting = true) }
        viewModelScope.launch {
            medicalRecordService
                .createMedicalRecord(record, uploads)
                .onSuccess { created ->
                    val notStored = created.attachments.count {
                        it.status == AttachmentStatus.UPLOAD_FAILED || it.status == AttachmentStatus.INFECTED
                    }
                    val message = if (notStored > 0) {
                        UiText.Resource(UiRes.string.record_created_with_failed_files, arrayOf<Any>(notStored))
                            .asStringAsync()
                    } else {
                        created.message.ifBlank { null }
                    }
                    eventChannel.send(CreateRecordEvent.RecordCreatedSuccessfully)
                    // Leave isSubmitting = true: the screen is closing and must not create again
                    message?.let { SnackbarController.sendEvent(SnackbarEvent(message = it)) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isSubmitting = false) }
                    when (error) {
                        DataError.Remote.Status.CONFLICT ->
                            showMessage(UiText.Resource(UiRes.string.record_error_already_exists))
                        DataError.Remote.Status.NOT_FOUND ->
                            showMessage(UiText.Resource(UiRes.string.record_error_appointment_not_found))
                        else -> SnackbarController.sendEvent(SnackbarEvent(error = error))
                    }
                }
        }
    }

    private fun showMessage(text: UiText) {
        viewModelScope.launch {
            SnackbarController.sendEvent(SnackbarEvent(message = text.asStringAsync()))
        }
    }

    private fun getAppointment() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            appointmentService
                .getAppointmentById(appointmentId)
                .onSuccess { data ->
                    mainLogger.debug("Appointment ${data.data}")
                    data.data.patient?.let { patient ->
                        _uiState.update { state ->
                            state.copy(
                                form = state.form.copy(
                                    patientId = patient.id,
                                    appointmentId = appointmentId
                                )
                            )
                        }
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            appointment = data.data
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    SnackbarController.sendEvent(
                        SnackbarEvent(error = error)
                    )
                }
        }
    }

    private fun observeConnectivity() {
        connectivityObserver.isConnected
            .distinctUntilChanged()
            .onEach { connected ->
                mainLogger.debug("Connectivity = $connected")
                _uiState.update { it.copy(isConnected = connected) }
            }
            .launchIn(viewModelScope)
    }

    private fun onBodyPartSelected(part: BodyHitRegion) {
        _uiState.update { state ->
            val selectedParts = state.form.selectedBodyParts

            state.copy(
                form = state.form.copy(
                    selectedBodyParts = if (part in selectedParts) {
                        selectedParts - part
                    } else {
                        selectedParts + part
                    }
                )
            )
        }
    }

    private fun onBodyRegionSelected(region: BodyRegion) {
        _uiState.update { state ->
            state.copy(
                selectedRegion = region
            )
        }
    }

    private fun onMedRecordSelected(type: MedicalRecordType) {
        _uiState.update { state ->
            state.copy(
                form = state.form.copy(
                    medicalRecordType =  type
                )
            )
        }
    }

    private fun onTitleUpdated(title: String) {
        _uiState.update { state ->
            state.copy(
                form = state.form.copy(
                    title = title
                )
            )
        }
    }

    private fun onRecommendationsUpdated(recommendations: String) {
        _uiState.update { state ->
            state.copy(
                form = state.form.copy(
                    recommendations = recommendations
                )
            )
        }
    }

    private fun onDiagnosisUpdated(diagnosis: String) {
        _uiState.update { state ->
            state.copy(
                form = state.form.copy(
                    diagnosis = diagnosis
                )
            )
        }
    }

    private fun onTabSelected(tab: CreateRecordTab) {
        _uiState.update { state ->
            state.copy(
                currentTab = tab
            )
        }
    }
}

private fun CreateRecordForm.toNewMedicalRecord() = NewMedicalRecord(
    appointmentId = appointmentId,
    patientId = patientId,
    title = title,
    diagnosis = diagnosis,
    recommendations = recommendations,
    type = medicalRecordType,
    bodyParts = selectedBodyParts.map { BodyPartSelection(part = it.part, region = it.region) }.toSet()
)