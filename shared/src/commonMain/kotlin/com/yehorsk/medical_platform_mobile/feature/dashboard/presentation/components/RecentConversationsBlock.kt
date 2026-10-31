package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel.DashboardState
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.no_recent_messages
import medicalplatformmobile.shared.generated.resources.recent_messages
import org.jetbrains.compose.resources.stringResource

/** "Recent messages" on the home screen: the latest conversations as cards. */
@Composable
fun RecentConversationsBlock(
    state: DashboardState,
    onSeeAllClick: () -> Unit,
    onConversationClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ContentBlock(
        modifier = modifier,
        title = stringResource(UiRes.string.recent_messages),
        seeAllButtonClicked = onSeeAllClick,
        content = {
            val authData = state.authData
            when {
                state.recentConversations.isNotEmpty() && authData != null -> {
                    Column(
                        // Room for the cards' shadow
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.recentConversations.forEach { conversation ->
                            DashChatItem(
                                conversation = conversation,
                                localUserId = authData.user.id,
                                onClick = { onConversationClick(conversation.id) }
                            )
                        }
                    }
                }
                // First load only; a refresh keeps showing the previous list
                state.isLoadingConversations -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    }
                }
                else -> {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        textAlign = TextAlign.Center,
                        text = stringResource(UiRes.string.no_recent_messages),
                        fontSize = 14.sp
                    )
                }
            }
        }
    )
}
