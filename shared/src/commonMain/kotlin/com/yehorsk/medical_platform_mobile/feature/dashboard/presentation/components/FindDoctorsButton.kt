package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_forward_24px
import medicalplatformmobile.shared.generated.resources.find_doctors
import medicalplatformmobile.shared.generated.resources.find_doctors_descr
import medicalplatformmobile.shared.generated.resources.stethoscope_24px
import medicalplatformmobile.shared.generated.resources.welcome_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FindDoctorsButton(
    modifier: Modifier = Modifier,
    navigateToDoctorsScreen: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.tertiary
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
            .clickable{
                navigateToDoctorsScreen()
            }
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(UiRes.drawable.stethoscope_24px),
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                        start = 10.dp
                    )
            ) {
                Text(
                    text = stringResource(UiRes.string.find_doctors),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = stringResource(UiRes.string.find_doctors_descr),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
        ){
            Image(
                painter = painterResource(UiRes.drawable.arrow_forward_24px),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
fun FindDoctorsButtonPreview(){
    AppTheme {
        FindDoctorsButton(
            navigateToDoctorsScreen = {}
        )
    }
}