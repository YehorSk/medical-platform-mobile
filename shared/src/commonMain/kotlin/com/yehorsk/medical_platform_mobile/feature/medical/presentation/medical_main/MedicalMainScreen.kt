package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.ui.components.cards.DefaultListCard
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.lab_profile_24px
import medicalplatformmobile.shared.generated.resources.medical_card
import medicalplatformmobile.shared.generated.resources.medical_information_24px
import medicalplatformmobile.shared.generated.resources.medical_records
import medicalplatformmobile.shared.generated.resources.my_health
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MedicalMainScreen(
    modifier: Modifier = Modifier,
    role: UserRole,
    navigateTo: (MedicalMainDestination) -> Unit
){
    MedicalMainScreenRoot(
        modifier = modifier,
        role = role,
        navigateTo = { navigateTo(it) }
    )
}

@Composable
fun MedicalMainScreenRoot(
    modifier: Modifier = Modifier,
    role: UserRole,
    navigateTo: (MedicalMainDestination) -> Unit
){
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.my_health),
            onGoBackClicked = { navigateTo(MedicalMainDestination.Back) }
        )
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                if(role == UserRole.PATIENT){
                    item {
                        DefaultListCard(
                            title = stringResource(UiRes.string.medical_card),
                            subtitle = "",
                            icon = painterResource(UiRes.drawable.medical_information_24px),
                            onClick = { navigateTo(MedicalMainDestination.MedicalCard) }
                        )
                    }
                    item {
                        DefaultListCard(
                            title = stringResource(UiRes.string.medical_records),
                            subtitle = "",
                            icon = painterResource(UiRes.drawable.lab_profile_24px),
                            onClick = { navigateTo(MedicalMainDestination.MedicalRecords) }
                        )
                    }
                }
            }
        }
    }
}