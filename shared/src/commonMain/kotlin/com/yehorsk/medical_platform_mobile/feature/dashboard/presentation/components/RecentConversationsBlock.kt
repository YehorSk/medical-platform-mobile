package com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.components.ConversationItem
import com.yehorsk.medical_platform_mobile.feature.dashboard.presentation.viewmodel.DashboardState
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.no_recent_messages
import medicalplatformmobile.shared.generated.resources.recent_messages
import org.jetbrains.compose.resources.stringResource

/** "Recent messages" on the home screen: the latest conversations, same rows as the chat list. */
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
                    state.recentConversations.forEachIndexed { index, conversation ->
                        ConversationItem(
                            conversation = conversation,
                            localUser = authData,
                            onClick = { onConversationClick(conversation.id) }
                        )
                        if (index < state.recentConversations.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.outline
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
