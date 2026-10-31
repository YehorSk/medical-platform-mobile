package com.yehorsk.medical_platform_mobile.feature.chat.domain.service

import com.yehorsk.medical_platform_mobile.core.data.network.ConnectionState
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.ConversationBlockReason
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.Message
import com.yehorsk.medical_platform_mobile.feature.chat.domain.model.PendingMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ChatConnectionClient {
    val chatMessages: Flow<Message>
    val connectionState: StateFlow<ConnectionState>

    // Sent but not yet confirmed by the server, oldest first
    val pendingMessages: StateFlow<List<PendingMessage>>

    // Conversations the server told us we can't write in (ACCESS_REVOKED / CONVERSATION_NOT_ACTIVE / FORBIDDEN)
    val blockedConversations: StateFlow<Map<String, ConversationBlockReason>>

    /** Queues the message; it's sent now if connected, otherwise after the next reconnect. */
    fun sendMessage(conversationId: String, content: String)
    fun retryMessage(localId: String)
    fun discardMessage(localId: String)

    /** Called when REST shows the relation is APPROVED again, so a stale block doesn't stick. */
    fun clearConversationBlock(conversationId: String)
}
