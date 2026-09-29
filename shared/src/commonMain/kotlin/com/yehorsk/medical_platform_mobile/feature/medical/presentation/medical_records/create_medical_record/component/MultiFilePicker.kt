package com.yehorsk.medical_platform_mobile.feature.medical.presentation.medical_records.create_medical_record.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path

@Composable
fun MultiFilePicker(
    files: List<PlatformFile>,
    onFilesPicked: (List<PlatformFile>) -> Unit,
    onFileRemoved: (PlatformFile) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Attachments",
    buttonText: String = "Add files",
    extensions: List<String>? = null,
    enabled: Boolean = true,
) {
    val launcher = rememberFilePickerLauncher(
        type = if (extensions.isNullOrEmpty()) {
            FileKitType.File()
        } else {
            FileKitType.File(extensions)
        },
        mode = FileKitMode.Multiple(),
    ) { picked ->
        if (picked != null) onFilesPicked(picked)
    }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (files.isEmpty()) label else "$label (${files.size})",
                style = MaterialTheme.typography.titleSmall
            )
            OutlinedButton(onClick = { launcher.launch() }, enabled = enabled) {
                Text(buttonText)
            }
        }

        files.forEach { file ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = file.name,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(onClick = { onFileRemoved(file) }, enabled = enabled) {
                        Text("Remove")
                    }
                }
            }
        }
    }
}