package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.domain.model.Conversation
import com.yehorsk.medical_platform_mobile.core.ui.components.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.components.ConversationItem
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel.ChatListAction
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel.ChatListState
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.viewmodel.ChatListViewModel
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatListViewModel = koinViewModel(),
    onConversationClick: (Conversation) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ChatListScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                is ChatListAction.OnConversationClick -> onConversationClick(action.conversation)
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun ChatListScreenRoot(
    modifier: Modifier = Modifier,
    state: ChatListState,
    onAction: (ChatListAction) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        AppTopBar(
            title = stringResource(UiRes.string.chat),
            onGoBackClicked = {}
        )
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.error != null -> {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                state.conversations.isEmpty() -> {
                    Text(
                        text = "No conversations yet",
                        color = Color(0xFF717182),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.conversations,
                            key = { it.id }
                        ) { conversation ->
                            ConversationItem(
                                conversation = conversation,
                                onClick = { onAction(ChatListAction.OnConversationClick(conversation)) }
                            )
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}