package com.yehorsk.medical_platform_mobile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yehorsk.medical_platform_mobile.feature.auth.ui.register.RegisterScreen

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ){ paddingValues ->
            RegisterScreen(
                modifier = Modifier
                    .padding(paddingValues)
            )
        }
    }
}