package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.ChatTopBar
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.EmptySection
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.JumpToLatestButton
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.MessageBox
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.MessageList
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.ParticipantDetailsDialog
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.rememberChatScrollState
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel.ChatDetailAction
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel.ChatDetailState
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel.ChatDetailViewModel
import kotlinx.coroutines.launch
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat_connecting
import medicalplatformmobile.shared.generated.resources.chat_message_too_long
import medicalplatformmobile.shared.generated.resources.chat_role_doctor
import medicalplatformmobile.shared.generated.resources.chat_role_patient
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatDetailScreen(
    modifier: Modifier = Modifier,
    chatId: String?,
    onGoBackClicked: () -> Unit,
    viewModel: ChatDetailViewModel = koinViewModel()
){
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(chatId) {
        viewModel.switchChat(chatId)
    }

    ChatDetailScreenRoot(
        modifier = modifier,
        state = state,
        onAction = { viewModel.onAction(it) },
        onGoBackClicked = { onGoBackClicked() }
    )
}

@Composable
fun ChatDetailScreenRoot(
    modifier: Modifier = Modifier,
    state: ChatDetailState,
    onAction: (ChatDetailAction) -> Unit,
    onGoBackClicked: () -> Unit,
) {
    val messageListState = rememberLazyListState()
    val scrollState = rememberChatScrollState(
        listState = messageListState,
        chatId = state.conversation?.id,
        messages = state.messages
    )
    val scope = rememberCoroutineScope()

    val conversation = state.conversation
    val isLocalPatient = conversation != null && state.localUserId == conversation.patient.userId
    val otherParticipant = when {
        conversation == null -> null
        isLocalPatient -> conversation.doctor
        else -> conversation.patient
    }
    val otherRole = stringResource(
        if (isLocalPatient) UiRes.string.chat_role_doctor else UiRes.string.chat_role_patient
    )
    val subtitle = when {
        conversation == null -> null
        state.isConnecting -> stringResource(UiRes.string.chat_connecting)
        else -> otherRole
    }
    var showParticipantDetails by rememberSaveable { mutableStateOf(false) }

    if (showParticipantDetails && otherParticipant != null) {
        ParticipantDetailsDialog(
            participant = otherParticipant,
            role = otherRole,
            onDismiss = { showParticipantDetails = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        ChatTopBar(
            participant = otherParticipant,
            subtitle = subtitle,
            onGoBackClicked = onGoBackClicked,
            onParticipantClick = { showParticipantDetails = true }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (state.error != null && state.messages.isEmpty() && !state.isLoading) {
                EmptySection(
                    title = state.error.asString(),
                    description = "",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                MessageList(
                    modifier = Modifier.fillMaxSize(),
                    messages = state.messages,
                    isPaginationLoading = state.isLoading,
                    listState = messageListState,
                    onMessageRetryClick = { message ->
                        message.localId?.let { onAction(ChatDetailAction.OnRetryMessageClick(it)) }
                    },
                    onMessageDiscardClick = { message ->
                        message.localId?.let { onAction(ChatDetailAction.OnDiscardMessageClick(it)) }
                    }
                )
                JumpToLatestButton(
                    scrollState = scrollState,
                    onClick = { scope.launch { scrollState.scrollToLatest() } },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }

        MessageBox(
            message = state.message,
            onMessageChange = { onAction(ChatDetailAction.OnMessageChange(it)) },
            isSendButtonEnabled = state.canSendMessage,
            onSendClick = { onAction(ChatDetailAction.OnSendMessageClick) },
            notice = state.inputNotice?.asString(),
            error = if (state.isMessageTooLong) stringResource(UiRes.string.chat_message_too_long) else "",
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(8.dp)
        )
    }
}
