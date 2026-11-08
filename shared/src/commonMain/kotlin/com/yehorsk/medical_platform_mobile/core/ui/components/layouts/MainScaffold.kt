package com.yehorsk.medical_platform_mobile.core.ui.components.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.other.AutoResizedText
import com.yehorsk.medical_platform_mobile.navigation.Graph
import com.yehorsk.medical_platform_mobile.navigation.Screen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    screenContent: @Composable () -> Unit,
    userRole: UserRole?,
    hideNavigation: Boolean = false
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Chat,
        BottomBarScreen.Connect,
        BottomBarScreen.Health,
        BottomBarScreen.Profile,
    ).filter { userRole in it.userRoles }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showNavigation = screens.map { it.screen::class }.any{ route ->
        currentDestination?.hierarchy?.any{
            it.hasRoute(route)
        } == true
    }
    val isAuthGraph = navBackStackEntry
        ?.destination
        ?.hierarchy
        ?.any { it.hasRoute<Graph.Authentication>() } == true

    BoxWithConstraints(modifier.fillMaxSize()) {
        val suiteType = navigationSuiteTypeFor(maxWidth)

        val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

        val layoutType = when {
            isAuthGraph -> NavigationSuiteType.None
            suiteType == NavigationSuiteType.NavigationRail -> NavigationSuiteType.NavigationRail
            showNavigation && !hideNavigation && !isImeVisible -> suiteType
            else -> NavigationSuiteType.None
        }
        NavigationSuiteScaffold(
            layoutType = layoutType,
            navigationSuiteItems = {
                screens.forEach { screen ->
                    val isSelected = currentDestination?.hierarchy?.any{ it.hasRoute(screen.screen::class) } == true
                    item(
                        selected = isSelected,
                        onClick = {
                            if(!isSelected){
                                navController.navigate(screen.screen){
                                    popUpTo<Screen.Home> {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(screen.icon),
                                contentDescription = stringResource(screen.title),
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        label = {
                            AutoResizedText(
                                text = stringResource(screen.title),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            },
        ) {
            val barInsets = when (layoutType) {
                NavigationSuiteType.NavigationBar ->
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                NavigationSuiteType.NavigationRail ->
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Start)
                else -> WindowInsets(0)
            }
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.windowInsetsPadding(
                            WindowInsets.safeDrawing
                                .only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)
                                .exclude(barInsets)
                        )
                    )
                },
                contentWindowInsets = WindowInsets(0)
            ) { contentPadding ->
                Box(
                    Modifier
                        .padding(contentPadding)
                        .consumeWindowInsets(barInsets)
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                ) { screenContent() }
            }
        }
    }

}

private fun navigationSuiteTypeFor(width: Dp): NavigationSuiteType = when {
    width >= 600.dp  -> NavigationSuiteType.NavigationRail
    else             -> NavigationSuiteType.NavigationBar
}