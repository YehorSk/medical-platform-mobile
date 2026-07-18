package com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.DefaultTextField
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.component.FilterBottomSheet
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorAction
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorState
import com.yehorsk.medical_platform_mobile.feature.connections.presentation.find_doctor.viewmodel.FindDoctorViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.filter_list_24px
import medicalplatformmobile.shared.generated.resources.find_a_doctor
import medicalplatformmobile.shared.generated.resources.search_24px
import medicalplatformmobile.shared.generated.resources.search_doctors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FindDoctorScreen(
    modifier: Modifier = Modifier,
    viewModel: FindDoctorViewModel = koinViewModel(),
    goBack: () -> Unit
){

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    FindDoctorScreenRoot(
        modifier = modifier,
        goBack = { goBack() },
        state = state,
        onAction = { viewModel.onAction(it) }
    )

}

@Composable
fun FindDoctorScreenRoot(
    modifier: Modifier = Modifier,
    state: FindDoctorState,
    onAction: (FindDoctorAction) -> Unit,
    goBack: () -> Unit
){
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.find_a_doctor),
            showGoBackButton = true,
            onGoBackClicked = { goBack() }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DefaultTextField(
                    modifier = Modifier.weight(1f),
                    leadingIcon = painterResource(UiRes.drawable.search_24px),
                    leadingIconDescr = "",
                    onValueChange = { onAction(FindDoctorAction.OnSearchTextChanged(it)) },
                    value = state.form.search,
                    placeholder = stringResource(UiRes.string.search_doctors)
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.LightGray.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(10.dp)
                        .clickable{
                            onAction(FindDoctorAction.ShowFilterBottomSheet)
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(UiRes.drawable.filter_list_24px),
                        contentDescription = null
                    )
                }
            }
        }
    }
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
    if(state.showFilterBottomSheet){
        FilterBottomSheet(
            onDismiss = { onAction(FindDoctorAction.ShowFilterBottomSheet) },
            state = state,
            onSpecializationClicked = { onAction(FindDoctorAction.OnSpecializationClicked(it)) },
            onCityValueChanged = { onAction(FindDoctorAction.OnCityTextChanged(it)) },
            onApplyFiltersClicked = { onAction(FindDoctorAction.OnApplyFiltersClicked) },
        )
    }
}