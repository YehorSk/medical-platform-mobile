package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultDropdownTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    header: String? = null,
    placeholder: String = "",
    leadingIcon: Painter? = null,
    leadingIconDescr: String = "",
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    error: String = ""
) {
    var expanded by remember { mutableStateOf(false) }

    header?.let {
        Text(it, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
            value = value,
            onValueChange = {}, // read-only, selection happens via menu
            readOnly = true,
            leadingIcon = leadingIcon?.let { painter ->
                {
                    Icon(
                        painter = painter,
                        contentDescription = leadingIconDescr
                    )
                }
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
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
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(0xFF2B5CE6)
            ),
            singleLine = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultDropdownTextFieldPreview() {
    AppTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                var selectedCountry by remember { mutableStateOf("") }

                DefaultDropdownTextField(
                    header = "Country",
                    placeholder = "Select a country",
                    value = selectedCountry,
                    options = listOf("Slovakia", "Czechia", "Poland", "Austria"),
                    onOptionSelected = { selectedCountry = it }
                )
            }
        }
    }
}
