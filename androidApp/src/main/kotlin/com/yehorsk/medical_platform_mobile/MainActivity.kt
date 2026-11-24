package com.yehorsk.medical_platform_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yehorsk.medical_platform_mobile.core.data.push.PushIntentHandler
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.manualFileKitCoreInitialization

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        FileKit.manualFileKitCoreInitialization(this)
        // On recreation (e.g. rotation) the notification was already handled.
        if (savedInstanceState == null) {
            PushIntentHandler.handle(intent)
        }
        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        PushIntentHandler.handle(intent)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
