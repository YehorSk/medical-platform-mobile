package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.upcoming_appointments
import medicalplatformmobile.shared.generated.resources.view_all
import org.jetbrains.compose.resources.stringResource

@Composable
fun ContentBlock(
    modifier: Modifier = Modifier,
    title: String,
    seeAllButtonClicked: () -> Unit,
    content: @Composable () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = title
            )

            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .clickable(
                        enabled = true,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) { seeAllButtonClicked() },
                text = stringResource(UiRes.string.view_all),
                color = MaterialTheme.colorScheme.primary
            )
        }

        content()
    }
}

@Preview(showBackground = true)
@Composable
fun ContentBlockPreview() {
    AppTheme {
        ContentBlock(
            title = stringResource(UiRes.string.upcoming_appointments),
            seeAllButtonClicked = {}
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(listOf("Item 1", "Item 2", "Item 3")) { item ->
                    Text(
                        text = item,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}