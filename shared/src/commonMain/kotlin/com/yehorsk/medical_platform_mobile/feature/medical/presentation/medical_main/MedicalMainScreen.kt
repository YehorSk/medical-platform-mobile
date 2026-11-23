package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.cards.DefaultGridCard
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.core.util.currentWindowLayout
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.lab_profile_24px
import medicalplatformmobile.shared.generated.resources.medical_card
import medicalplatformmobile.shared.generated.resources.medical_information_24px
import medicalplatformmobile.shared.generated.resources.medical_records
import medicalplatformmobile.shared.generated.resources.my_health
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
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
    val large = currentWindowLayout().isExpanded
    val menuItems = remember(role) { medicalMenuItems(role) }

    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.my_health),
            onGoBackClicked = { navigateTo(MedicalMainDestination.Back) }
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = if (large) 220.dp else 150.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(if (large) 24.dp else 16.dp),
            horizontalArrangement = Arrangement.spacedBy(if (large) 20.dp else 12.dp),
            verticalArrangement = Arrangement.spacedBy(if (large) 20.dp else 12.dp)
        ) {
            items(menuItems, key = { it.destination.toString() }) { item ->
                DefaultGridCard(
                    title = stringResource(item.title),
                    icon = painterResource(item.icon),
                    large = large,
                    onClick = { navigateTo(item.destination) }
                )
            }
        }
    }
}

private data class MedicalMenuItem(
    val title: StringResource,
    val icon: DrawableResource,
    val destination: MedicalMainDestination
)

private fun medicalMenuItems(role: UserRole): List<MedicalMenuItem> = buildList {
    if (role == UserRole.PATIENT) {
        add(MedicalMenuItem(UiRes.string.medical_card, UiRes.drawable.medical_information_24px, MedicalMainDestination.MedicalCard))
        add(MedicalMenuItem(UiRes.string.medical_records, UiRes.drawable.lab_profile_24px, MedicalMainDestination.MedicalRecords))
    }
}
