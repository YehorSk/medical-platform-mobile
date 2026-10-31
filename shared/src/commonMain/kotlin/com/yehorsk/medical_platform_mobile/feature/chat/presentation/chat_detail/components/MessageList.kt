package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.no_messages
import medicalplatformmobile.shared.generated.resources.no_messages_subtitle
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageList(
    messages: List<MessageUi>,
    isPaginationLoading: Boolean,
    listState: LazyListState,
    onMessageRetryClick: (MessageUi.LocalUserMessage) -> Unit,
    modifier: Modifier = Modifier,
    onMessageDiscardClick: (MessageUi.LocalUserMessage) -> Unit = {}
) {
    if(messages.isEmpty() && !isPaginationLoading) {
        Box(
            modifier = modifier
                .padding(vertical = 32.dp),
            contentAlignment = Alignment.Center
        ) {
            EmptySection(
                title = stringResource(UiRes.string.no_messages),
                description = stringResource(UiRes.string.no_messages_subtitle),
            )
        }
    } else {
        // Ids this list has already displayed. Bubbles that come back into view (e.g. after the
        // jump-to-latest scroll) must not fade in again as if they had just arrived.
        val shownIds = remember { mutableSetOf<String>() }

        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = PaddingValues(16.dp),
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items = messages,
                key = { it.id }
            ) { message ->
                // Decided once per composition of the item: true only the first time this id shows up
                val isFirstAppearance = remember(message.id) { shownIds.add(message.id) }
                MedConnectMessageListItem(
                    messageUi = message,
                    onRetryClick = onMessageRetryClick,
                    onDiscardClick = onMessageDiscardClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItem(
                            fadeInSpec = if (isFirstAppearance) spring(stiffness = Spring.StiffnessMediumLow) else null
                        )
                )
            }

            when {
                isPaginationLoading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
