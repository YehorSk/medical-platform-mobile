package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.AppState
import com.yehorsk.medical_platform_mobile.core.ui.components.DashboardTopBar
import com.yehorsk.medical_platform_mobile.util.appointments
import com.yehorsk.medical_platform_mobile.util.conversations
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components.ContentBlock
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components.DashAppointmentItem
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components.DashChatItem
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components.FindDoctorsButton
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.no_appointments
import medicalplatformmobile.shared.generated.resources.no_recent_messages
import medicalplatformmobile.shared.generated.resources.recent_messages
import medicalplatformmobile.shared.generated.resources.upcoming_appointments
import org.jetbrains.compose.resources.stringResource

@Composable
fun PatientDashboardScreen(
    modifier: Modifier = Modifier
){
    PatientDashboardScreenRoot(
        modifier = modifier
    )
}

@Composable
fun PatientDashboardScreenRoot(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DashboardTopBar(
            state = AppState(
                user = User(
                    id = 0,
                    email = "test@gmail.com",
                    firstName = "John",
                    lastName = "Doe",
                    role = UserRole.PATIENT,
                    title = "Bc."
                ),
                notificationCount = 23
            ),
            navigateToNotifications = {}
        )
        FindDoctorsButton(
            modifier = Modifier
                .padding(top = 12.dp, start = 12.dp, end = 12.dp),
            navigateToDoctorsScreen = {

            }
        )
//        ContentBlock(
//            modifier = Modifier
//                .padding(horizontal = 12.dp),
//            title = stringResource(UiRes.string.quick_actions),
//            showSeeAllButton = false,
//            seeAllButtonClicked = {},
//            content = {
//            }
//        )
        ContentBlock(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            title = stringResource(UiRes.string.recent_messages),
            seeAllButtonClicked = {},
            content = {
                if (conversations.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(UiRes.string.no_recent_messages),
                        fontSize = 14.sp
                    )
                }else{
                    conversations.forEach { conversation ->
                        DashChatItem(
                            message = conversation.lastMessage,
                            onClick = {  }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        )
        ContentBlock(
            modifier = Modifier
                .padding(horizontal = 12.dp),
            title = stringResource(UiRes.string.upcoming_appointments),
            seeAllButtonClicked = {},
            content = {
                if (appointments.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(UiRes.string.no_appointments),
                        fontSize = 14.sp
                    )
                }else{
                    appointments.forEach { appointment ->
                        DashAppointmentItem(
                            appointment = appointment,
                            onClick = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PatientDashboardScreenPreview(){
    AppTheme {
        PatientDashboardScreenRoot()
    }
}