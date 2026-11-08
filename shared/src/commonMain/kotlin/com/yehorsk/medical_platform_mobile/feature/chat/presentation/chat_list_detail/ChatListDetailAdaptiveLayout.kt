package com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list_detail

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.snap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.ChatDetailScreen
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_detail.components.EmptySection
import com.yehorsk.medical_platform_mobile.feature.chat.presentation.chat_list.ChatListScreen
import kotlinx.coroutines.launch
import medicalplatformmobile.shared.generated.resources.UiRes
import medicalplatformmobile.shared.generated.resources.chat_select_conversation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Chat list and open chat side by side on wide screens (like Telegram),
 * one pane at a time on phones.
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatListDetailAdaptiveLayout(
    initialChatId: String?,
    modifier: Modifier = Modifier,
    onFullScreenDetailChange: (Boolean) -> Unit = {},
    chatListDetailViewModel: ChatListDetailViewModel = koinViewModel()
) {
    val sharedState by chatListDetailViewModel.state.collectAsStateWithLifecycle()
    val scaffoldDirective = createNoSpacingPaneScaffoldDirective()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Nothing>(
        scaffoldDirective = scaffoldDirective
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(initialChatId) {
        if (initialChatId != null) {
            chatListDetailViewModel.onAction(ChatListDetailAction.OnSelectChat(initialChatId))
            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
        }
    }

    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
            chatListDetailViewModel.onAction(ChatListDetailAction.OnSelectChat(null))
        }
    }

    // On a phone the detail pane gets hidden on back: drop the selection so the chat isn't reopened later
    val detailPane = scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail]
    LaunchedEffect(detailPane, sharedState.selectedChatId) {
        if (detailPane == PaneAdaptedValue.Hidden && sharedState.selectedChatId != null) {
            chatListDetailViewModel.onAction(ChatListDetailAction.OnSelectChat(null))
        }
    }

    // Only on a phone does the open chat take the whole screen; the app hides its bottom bar then
    val isFullScreenDetail = detailPane == PaneAdaptedValue.Expanded &&
            scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Hidden
    DisposableEffect(isFullScreenDetail) {
        onFullScreenDetailChange(isFullScreenDetail)
        onDispose { onFullScreenDetailChange(false) }
    }

    ListDetailPaneScaffold(
        directive = scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface),
        listPane = {
            AnimatedPane(
                enterTransition = EnterTransition.None,
                exitTransition = ExitTransition.None,
                boundsAnimationSpec = snap()
            ) {
                ChatListScreen(
                    selectedChatId = sharedState.selectedChatId,
                    onConversationClick = { conversation ->
                        chatListDetailViewModel.onAction(ChatListDetailAction.OnSelectChat(conversation.id))
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                        }
                    }
                )
            }
        },
        detailPane = {
            // Panes switch instantly: the default slide plus the chat loading made opening a chat feel laggy
            AnimatedPane(
                enterTransition = EnterTransition.None,
                exitTransition = ExitTransition.None,
                boundsAnimationSpec = snap()
            ) {
                val listPane = scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.List]
                val isListVisible = detailPane == PaneAdaptedValue.Expanded &&
                        listPane == PaneAdaptedValue.Expanded
                val chatId = sharedState.selectedChatId

                if (chatId == null) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        EmptySection(
                            title = stringResource(UiRes.string.chat_select_conversation),
                            description = "",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    ChatDetailScreen(
                        modifier = Modifier.fillMaxSize(),
                        chatId = chatId,
                        showGoBackButton = !isListVisible,
                        onGoBackClicked = {
                            scope.launch {
                                if (scaffoldNavigator.canNavigateBack()) {
                                    scaffoldNavigator.navigateBack()
                                }
                            }
                        }
                    )
                }
            }
        }
    )
}

/** Two panes from medium width up (tablet portrait included), with no gap between them. */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun createNoSpacingPaneScaffoldDirective(): PaneScaffoldDirective {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val directive = calculatePaneScaffoldDirective(adaptiveInfo)
    val maxHorizontalPartitions =
        if (adaptiveInfo.windowSizeClass.minWidthDp >= WIDTH_DP_MEDIUM_LOWER_BOUND) 2 else 1

    return directive.copy(
        maxHorizontalPartitions = maxHorizontalPartitions,
        horizontalPartitionSpacerSize = 0.dp,
        verticalPartitionSpacerSize = 0.dp
    )
}
