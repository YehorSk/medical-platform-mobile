package com.yehorsk.medical_platform_mobile.feature.settings.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.feature.settings.presentation.viewmodel.SettingsState
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_back_24px
import medicalplatformmobile.shared.generated.resources.edit_24px
import medicalplatformmobile.shared.generated.resources.go_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsMainHeader(
    modifier: Modifier = Modifier,
    state: SettingsState,
    showGoBackButton: Boolean = false,
    showUserData: Boolean = true,
    navigateToProfilePage: () -> Unit= {},
    onGoBackButtonClicked: () -> Unit= {}
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ){
        state.user?.let{ user ->
            Column {
                if(showGoBackButton){
                    Row(
                        modifier = Modifier
                            .padding(
                                bottom = 8.dp
                            )
                            .clickable(
                                indication = null,
                                onClick = {
                                    onGoBackButtonClicked()
                                },
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    ) {
                        Icon(
                            painter = painterResource(UiRes.drawable.arrow_back_24px),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            text = stringResource(UiRes.string.go_back),
                            fontSize = 18.sp,
                            color = Color.White,
                        )
                    }
                }
                if(showUserData){
                    Row {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .border(2.dp, Color.White, CircleShape)
                                .padding(16.dp)
                        ){
                            Text(
                                text = "${user.firstName[0]}${user.lastName[0]}",
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
                                text = "${user.title} ${user.firstName} ${user.lastName}",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Text(
                                text = state.user.email,
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
            if(!showGoBackButton){
                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = {
                        navigateToProfilePage()
                    }
                ){
                    Icon(
                        painter = painterResource(UiRes.drawable.edit_24px),
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
fun SettingsMainHeaderPreview(){
    AppTheme {
        SettingsMainHeader(
            showGoBackButton = true,
            state = SettingsState(
                user = User(
                    id = "0",
                    email = "test@gmail.com",
                    firstName = "John",
                    lastName = "Doe",
                    role = "PATIENT"
                )
            ),
            navigateToProfilePage = {}
        )
    }
}