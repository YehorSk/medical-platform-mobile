package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.core.domain.model.Message
import com.yehorsk.medical_platform_mobile.core.domain.model.User
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.util.formatTimeAgo
import com.yehorsk.medical_platform_mobile.util.toText

@Composable
fun DashChatItem(
    modifier: Modifier = Modifier,
    message: Message,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE0E0E0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message.sender.firstName.first().uppercaseChar().toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF717182)
                )
            }
            if (!message.isRead) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(Color(0xFF2B5CE6), CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${message.sender.title} ${message.sender.firstName} ${message.sender.lastName}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = message.content,
                fontSize = 14.sp,
                color = Color(0xFF717182),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = formatTimeAgo(message.createdAt.toString()).toText(),
            fontSize = 12.sp,
            color = Color(0xFF717182)
        )
    }
}

//@Preview
//@Composable
//fun DashChatItemPreview() {
//    val message = Message(
//        id = 1,
//        conversationId = 1,
//        sender = User(
//            id = 1,
//            email = "sarah@example.com",
//            firstName = "Sarah",
//            lastName = "Johnson",
//            role = UserRole.DOCTOR,
//            title = "Dr."
//        ),
//        content = "Your test results are ready",
//        isRead = false,
//        createdAt = "2026-06-03T10:30:00Z"
//    )
//    DashChatItem(
//        message = message,
//        onClick = {}
//    )
//}
