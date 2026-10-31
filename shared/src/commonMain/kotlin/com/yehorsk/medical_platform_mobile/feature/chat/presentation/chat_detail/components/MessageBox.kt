package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.core.ui.components.buttons.DefaultButton
import com.yehorsk.medical_platform_mobile.core.ui.components.textfields.DefaultMultilineTextField
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.send_24px
import org.jetbrains.compose.resources.painterResource

@Composable
fun MessageBox(
    message: String,
    onMessageChange: (String) -> Unit,
    isSendButtonEnabled: Boolean,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Message",
    isConnected: Boolean = true,
    // When set, writing is not allowed in this chat: the notice replaces the input
    notice: String? = null,
    error: String = ""
) {
    if (notice != null) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = notice,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
        return
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        DefaultMultilineTextField(
            modifier = Modifier
                .weight(1f)
                .onPreviewKeyEvent { keyEvent ->
                    val isModifierPressed = keyEvent.isMetaPressed || keyEvent.isCtrlPressed
                    val isSendShortcut = isModifierPressed
                            && keyEvent.key == Key.Enter
                            && keyEvent.type == KeyEventType.KeyDown

                    if (isSendShortcut) {
                        onSendClick()
                        true
                    } else false
                },
            value = message,
            onValueChange = onMessageChange,
            placeholder = placeholder,
            maxLines = 4,
            error = error
        )

        DefaultButton(
            modifier = Modifier.width(88.dp),
            leadingIcon = painterResource(UiRes.drawable.send_24px),
            onClick = onSendClick,
            enabled = isConnected && isSendButtonEnabled
        )
    }
}