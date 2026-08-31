package com.yehorsk.medical_platform_mobile.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = Color.Unspecified,
    text: String,
    leadingIcon: Painter? = null,
    isLoading: Boolean = false,
    isEnabled: Boolean = true
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        enabled = isEnabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(6.dp)
        ) {

            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(
                        alpha = if (isLoading) 1f else 0f
                    ),
                strokeWidth = 1.5.dp,
                color = Color.Black
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.alpha(
                    if (isLoading) 0f else 1f
                )
            ) {
                if (leadingIcon != null) {
                    Icon(
                        painter = leadingIcon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall,
                    color = textColor
                )
            }
        }
    }
}