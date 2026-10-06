package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.medical_platform_mobile.core.ui.components.layouts.AppTopBar
import com.yehorsk.medical_platform_mobile.feature.appointments.presentation.appointment_details.viewmodel.AppointmentDetailsAction
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel.ChatDetailAction
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel.ChatDetailState
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.viewmodel.ChatDetailViewModel
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.appointment
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        AppTopBar(
            title = stringResource(UiRes.string.appointment),
            showGoBackButton = true,
            onGoBackClicked = onGoBackClicked
        )

        // reverseLayout: список идёт от новых к старым, новые внизу
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(state.messages, key = { it.id }) { message ->
                when (message) {
                    is MessageUi.LocalUserMessage -> Text(text = "Me: ${message.content}")
                    is MessageUi.OtherUserMessage -> Text(text = "${message.sender.firstName}: ${message.content}")
                    is MessageUi.DateSeparator -> Text(text = message.date.asString())
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = state.message,
                onValueChange = { onAction(ChatDetailAction.OnMessageChange(it)) },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Message") },
                maxLines = 4
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { onAction(ChatDetailAction.OnSendMessageClick) },
                enabled = state.canSendMessage
            ) {
                Text("Send")
            }
        }
    }
}