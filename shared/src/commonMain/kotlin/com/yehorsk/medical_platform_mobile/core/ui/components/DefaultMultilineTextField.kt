package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme

@Composable
fun DefaultMultilineTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    header: String? = null,
    placeholder: String = "",
    leadingIcon: Painter? = null,
    leadingIconDescr: String = "",
    minLines: Int = 4,
    maxLines: Int = Int.MAX_VALUE,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    error: String = ""
) {
    Column(
        modifier = modifier
    ) {
        header?.let {
            Text(
                text = it,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            leadingIcon = leadingIcon?.let { painter ->
                {
                    Icon(
                        painter = painter,
                        contentDescription = leadingIconDescr
                    )
                }
            },
            placeholder = {
                Text(text = placeholder)
            },
            isError = error.isNotBlank(),
            supportingText = {
                if (error.isNotBlank()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                errorBorderColor = MaterialTheme.colorScheme.error
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            singleLine = false,
            minLines = minLines,
            maxLines = maxLines
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultMultilineTextFieldPreview() {
    AppTheme {
        DefaultMultilineTextField(
            modifier = Modifier.padding(16.dp),
            header = "Notes",
            placeholder = "Enter additional notes...",
            value = "Patient prefers afternoon appointments.\nPlease call before arrival.",
            minLines = 5,
            onValueChange = {}
        )
    }
}