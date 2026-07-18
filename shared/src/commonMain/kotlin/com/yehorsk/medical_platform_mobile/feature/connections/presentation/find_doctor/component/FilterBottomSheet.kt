package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import com.yehorsk.medical_platform_mobile.core.domain.model.Specialization
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorState
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.apply_filters
import medicalplatformmobile.shared.generated.resources.city
import medicalplatformmobile.shared.generated.resources.filter_doctors
import medicalplatformmobile.shared.generated.resources.search_city
import medicalplatformmobile.shared.generated.resources.specialization
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss:()->Unit,
    state: FindDoctorState,
    onSpecializationClicked: (Specialization) -> Unit,
    onCityValueChanged: (String) -> Unit,
    onApplyFiltersClicked: () -> Unit,
){
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        content = {
            FilterBottomSheetContent(
                modifier = modifier,
                state = state,
                onSpecializationClicked = { onSpecializationClicked(it) },
                onCityValueChanged = { onCityValueChanged(it) },
                onApplyFiltersClicked = { onApplyFiltersClicked() }
            )
        }
    )
}

@Composable
fun FilterBottomSheetContent(
    modifier: Modifier = Modifier,
    state: FindDoctorState,
    onSpecializationClicked: (Specialization) -> Unit,
    onCityValueChanged: (String) -> Unit,
    onApplyFiltersClicked: () -> Unit,
){
    BoxWithConstraints {
        val widthModifier = if (this.maxWidth < 400.dp) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.width(640.dp)
        }
        Column(
            modifier = modifier
                .then(widthModifier)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                text = stringResource(UiRes.string.filter_doctors),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
            Column(
                modifier = Modifier.padding(top = 8.dp, start = 32.dp, end = 32.dp),
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(UiRes.string.specialization)
                )
                Spacer(modifier = Modifier.height(8.dp))
                SpecializationFlowRow(
                    items = state.specializations,
                    selectedItems = state.form.selectedSpecializations,
                    onItemClick = { onSpecializationClicked(it) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(UiRes.string.city)
                )
                Spacer(modifier = Modifier.height(8.dp))
                DefaultTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onValueChange = { onCityValueChanged(it) },
                    value = state.form.city,
                    placeholder = stringResource(UiRes.string.search_city)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { onApplyFiltersClicked() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    enabled = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = stringResource(UiRes.string.apply_filters),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun FilterBottomSheetContentPreview(){
    AppTheme {
        FilterBottomSheetContent(
            state = FindDoctorState(),
            onSpecializationClicked = {},
            onCityValueChanged = {},
            onApplyFiltersClicked = {},
        )
    }
}