package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.arrow_back_24px
import medicalplatformmobile.shared.generated.resources.messages
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onGoBackClicked: () -> Unit,
    showGoBackButton: Boolean = false
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showGoBackButton) {
            Icon(
                painter = painterResource(UiRes.drawable.arrow_back_24px),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    onGoBackClicked()
                }
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 10.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun AppTopBarPreview(){
    AppTheme {
        AppTopBar(
            title = stringResource(UiRes.string.messages),
            onGoBackClicked = {},
            showGoBackButton = true
        )
    }
}