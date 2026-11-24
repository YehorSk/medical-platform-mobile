package com.yehorsk.medical_platform_mobile.core.util

import kotlin.concurrent.Volatile

object ActiveChatTracker {

    @Volatile
    private var activeConversationId: String? = null

    internal fun setActiveChat(conversationId: String) {
        activeConversationId = conversationId
    }

    internal fun clearActiveChat(conversationId: String) {
        if (activeConversationId == conversationId) activeConversationId = null
    }

    fun isChatActive(conversationId: String): Boolean =
        activeConversationId == conversationId
}
