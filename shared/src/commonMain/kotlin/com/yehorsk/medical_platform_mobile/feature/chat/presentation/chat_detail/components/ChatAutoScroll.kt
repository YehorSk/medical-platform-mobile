package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.model.MessageUi
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat_scroll_to_latest
import org.jetbrains.compose.resources.stringResource

// The list is reverseLayout, so index 0 is the newest message.
// "At the bottom" still holds with a couple of messages scrolled past, like in Telegram.
private const val NEAR_BOTTOM_ITEMS = 3

// Animating through hundreds of items is slow; jump close first, then animate the rest
private const val MAX_ANIMATED_ITEMS = 15

@Stable
class ChatScrollState(val listState: LazyListState) {
    val isNearBottom by derivedStateOf { listState.firstVisibleItemIndex <= NEAR_BOTTOM_ITEMS }

    // Messages from the other side that arrived while the user was reading history
    var unreadCount by mutableIntStateOf(0)
        internal set

    internal var knownIds by mutableStateOf(emptySet<String>())

    suspend fun scrollToLatest() {
        if (listState.firstVisibleItemIndex > MAX_ANIMATED_ITEMS) {
            listState.scrollToItem(MAX_ANIMATED_ITEMS)
        }
        listState.animateScrollToItem(0)
        unreadCount = 0
    }
}

/**
 * Telegram-like behaviour for the message list:
 * - a message the user just sent always scrolls to the bottom;
 * - an incoming message scrolls there only if the user is already at (or next to) the bottom;
 * - otherwise it's counted, and [JumpToLatestButton] shows the count.
 */
@Composable
fun rememberChatScrollState(
    listState: LazyListState,
    chatId: String?,
    messages: List<MessageUi>
): ChatScrollState {
    val scrollState = remember(listState, chatId) { ChatScrollState(listState) }

    // A different chat starts at its newest message
    LaunchedEffect(scrollState) {
        listState.scrollToItem(0)
    }

    LaunchedEffect(scrollState, messages) {
        val previousIds = scrollState.knownIds
        val items = messages.filterNot { it is MessageUi.DateSeparator }
        scrollState.knownIds = items.mapTo(mutableSetOf()) { it.id }
        // First load of this chat: nothing "arrived", the list just opens at the bottom
        if (previousIds.isEmpty()) return@LaunchedEffect

        // Only items newer than everything already shown count as arrived; a reload that
        // fills in older history (e.g. after a reconnect) must not count as new messages
        val firstKnownIndex = items.indexOfFirst { it.id in previousIds }
        val arrived = (if (firstKnownIndex >= 0) items.take(firstKnownIndex) else items)
            .filter { it.id !in previousIds }
        if (arrived.isEmpty()) return@LaunchedEffect

        // localId is only set on messages sent from this device and not yet confirmed
        val justSent = arrived.any { it is MessageUi.LocalUserMessage && it.localId != null }
        when {
            justSent || scrollState.isNearBottom -> scrollState.scrollToLatest()
            else -> scrollState.unreadCount += arrived.count { it is MessageUi.OtherUserMessage }
        }
    }

    // Scrolling down by hand counts as reading them
    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.isNearBottom }
            .collect { nearBottom -> if (nearBottom) scrollState.unreadCount = 0 }
    }

    return scrollState
}

@Composable
fun JumpToLatestButton(
    scrollState: ChatScrollState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = !scrollState.isNearBottom,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        modifier = modifier
    ) {
        BadgedBox(
            badge = {
                if (scrollState.unreadCount > 0) {
                    Badge(containerColor = MaterialTheme.colorScheme.primary) {
                        Text(text = if (scrollState.unreadCount > 99) "99+" else "${scrollState.unreadCount}")
                    }
                }
            }
        ) {
            SmallFloatingActionButton(
                onClick = onClick,
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(UiRes.string.chat_scroll_to_latest)
                )
            }
        }
    }
}
