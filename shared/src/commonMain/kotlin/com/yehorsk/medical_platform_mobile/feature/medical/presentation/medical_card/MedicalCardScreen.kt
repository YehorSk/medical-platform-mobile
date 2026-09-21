package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultAssistChip
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultContentCard
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultDropdownTextField
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.BloodType
import com.yehorsk.medical_platform_mobile.feature.appointments.domain.model.Gender
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.InsuranceCompany
import com.yehorsk.medical_platform_mobile.feature.medical.domain.models.MedicalRecordType
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel.MedicalCardAction
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel.MedicalCardState
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_card.viewmodel.MedicalCardViewModel
import com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.viewmodel.CreateRecordAction
import com.yehorsk.medical_platform_mobile.util.toDisplayName
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.blood_type_title
import medicalplatformmobile.shared.generated.resources.create_record
import medicalplatformmobile.shared.generated.resources.gender_male
import medicalplatformmobile.shared.generated.resources.gender_title
import medicalplatformmobile.shared.generated.resources.insurance_policy_member_id
import medicalplatformmobile.shared.generated.resources.insurance_provider
import medicalplatformmobile.shared.generated.resources.insurance_title
import medicalplatformmobile.shared.generated.resources.medical_card
import medicalplatformmobile.shared.generated.resources.save_medical_card
import medicalplatformmobile.shared.generated.resources.shield_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MedicalCardScreen(
    modifier: Modifier = Modifier,
    viewModel: MedicalCardViewModel = koinViewModel(),
    onGoBackClicked: () -> Unit,
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    MedicalCardScreenRoot(
        modifier = modifier,
        onAction = { action ->
            when(action){
                MedicalCardAction.OnGoBackClicked -> onGoBackClicked()
                else -> viewModel.onAction(action)
            }
        },
        state = uiState
    )
}

@Composable
fun MedicalCardScreenRoot(
    modifier: Modifier = Modifier,
    onAction: (MedicalCardAction) -> Unit,
    state: MedicalCardState
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.medical_card),
            showGoBackButton = true,
            onGoBackClicked = { onAction(MedicalCardAction.OnGoBackClicked) }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column {
                DefaultContentCard(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    title = stringResource(UiRes.string.gender_title),
                    content = {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            maxItemsInEachRow = 2,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Gender.entries.forEach { type ->
                                DefaultAssistChip(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp),
                                    textStyle = MaterialTheme.typography.labelLarge,
                                    label = type.toDisplayName().asString(),
                                    isSelected = state.form.gender == type,
                                    onClick = {
                                        onAction(MedicalCardAction.GenderSelected(type))
                                    }
                                )
                            }
                        }
                    }
                )
                DefaultContentCard(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    title = stringResource(UiRes.string.blood_type_title),
                    content = {
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            maxItemsInEachRow = 4,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            BloodType.entries.dropLast(1).forEach { type ->
                                DefaultAssistChip(
                                    modifier = Modifier
                                        .weight(1f),
                                    textStyle = MaterialTheme.typography.labelLarge,
                                    label = type.toDisplayName().asString(),
                                    isSelected = state.form.bloodType == type,
                                    onClick = {
                                        onAction(MedicalCardAction.BloodTypeSelected(type))
                                    }
                                )
                            }
                        }
                    }
                )
                DefaultContentCard(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    leadingIcon = painterResource(UiRes.drawable.shield_24px),
                    title = stringResource(UiRes.string.insurance_title),
                    content = {
                        Column {
                            DefaultDropdownTextField(
                                value = state.form.insuranceProvider,
                                onOptionSelected = {
                                    onAction(
                                        MedicalCardAction.InsuranceProviderChanged(it)
                                    )
                                },
                                placeholder = stringResource(UiRes.string.insurance_provider),
                                header = stringResource(UiRes.string.insurance_provider),
                                options = InsuranceCompany.entries,
                                optionText = {
                                    it.toString()
                                },
                            )
                            DefaultTextField(
                                value = state.form.insurancePolicyMemberId,
                                onValueChange = {
                                    onAction(
                                        MedicalCardAction.InsurancePolicyMemberIdChanged(it)
                                    )
                                },
                                placeholder = stringResource(UiRes.string.insurance_policy_member_id),
                                header = stringResource(UiRes.string.insurance_policy_member_id)
                            )
                        }
                    }
                )
                DefaultButton(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    text = stringResource(UiRes.string.save_medical_card),
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun MedicalCardScreenRootPreview(){
    AppTheme {
        MedicalCardScreenRoot(
            onAction = {},
            state = MedicalCardState()
        )
    }
}