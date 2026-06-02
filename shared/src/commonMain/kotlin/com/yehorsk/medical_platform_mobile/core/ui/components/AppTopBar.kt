package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.ui.AppState
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.notifications_24px
import medicalplatformmobile.shared.generated.resources.welcome_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    state: AppState,
    navigateToNotifications: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp)
    ){
        state.user?.let {
            Row {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .border(2.dp, Color.White, CircleShape)
                        .padding(16.dp)
                ){
                    Text(
                        text = "${state.user.firstName[0]}${state.user.lastName[0]}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(
                            start = 10.dp
                        )
                ) {
                    Text(
                        text = stringResource(UiRes.string.welcome_back),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "${state.user.firstName} ${state.user.lastName}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            }
            IconButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClick = {
                    navigateToNotifications()
                }
            ){
                BadgedBox(
                    badge = {
                        if(state.notificationCount > 0){
                            Badge(
                                contentColor = Color.Red
                            ) {
                                Text(
                                    text = if (state.notificationCount > 99) "99+" else state.notificationCount.toString(),
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                ){
                    Icon(
                        painter = painterResource(UiRes.drawable.notifications_24px),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppTopBarPreview(){
    AppTheme {
        AppTopBar(
            state = AppState(
                user = User(
                    id = 0,
                    email = "test@gmail.com",
                    firstName = "John",
                    lastName = "Doe",
                    role = "patient"
                ),
                notificationCount = 23
            ),
            navigateToNotifications = {}
        )
    }
}