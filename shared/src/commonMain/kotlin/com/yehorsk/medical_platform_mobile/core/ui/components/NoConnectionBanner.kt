package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.error_no_internet
import medicalplatformmobile.shared.generated.resources.no_internet
import medicalplatformmobile.shared.generated.resources.wifi_off_24px
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun NoConnectionBanner(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .padding(bottom = 16.dp),
            painter = painterResource(UiRes.drawable.wifi_off_24px),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onErrorContainer
        )
        Text(
            text = stringResource(UiRes.string.no_internet),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onErrorContainer
        )
    }
}

@Preview
@Composable
fun NoConnectionBannerPreview(){
    AppTheme {
        NoConnectionBanner(
            modifier = Modifier.fillMaxSize()
        )
    }
}