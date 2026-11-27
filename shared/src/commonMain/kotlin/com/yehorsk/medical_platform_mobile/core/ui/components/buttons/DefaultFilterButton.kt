package com.yehorsk.medical_platform_mobile.core.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.theme.AppTheme
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.filter_list_24px
import medicalplatformmobile.shared.generated.resources.filters
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// Square filter tile, the same height as DefaultTextField. Shows a dot when filters are active.
@Composable
fun DefaultFilterButton(
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(OutlinedTextFieldDefaults.MinHeight)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray.copy(alpha = 0.4f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(UiRes.drawable.filter_list_24px),
            contentDescription = stringResource(UiRes.string.filters)
        )
        if (isActive) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(8.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            )
        }
    }
}

@Preview
@Composable
private fun DefaultFilterButtonPreview() {
    AppTheme {
        DefaultFilterButton(isActive = true, onClick = {})
    }
}
