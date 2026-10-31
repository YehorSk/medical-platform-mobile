package com.yehorsk.medical_platform_mobile.core.ui.components.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yehorsk.medical_platform_mobile.core.domain.model.UserRole
import com.yehorsk.medical_platform_mobile.core.ui.components.other.AutoResizedText
import com.yehorsk.medical_platform_mobile.navigation.Graph
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    screenContent: @Composable () -> Unit,
    userRole: UserRole?
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

        val layoutType = when {
            isAuthGraph -> NavigationSuiteType.None
            suiteType == NavigationSuiteType.NavigationRail -> NavigationSuiteType.NavigationRail
            showNavigation -> suiteType
            else -> NavigationSuiteType.None
        }
        NavigationSuiteScaffold(
            layoutType = layoutType,
            navigationSuiteItems = {
                screens.forEach { screen ->
                    val isSelected = currentDestination?.hierarchy?.any{ it.route == screen.screen::class.qualifiedName} == true
                    item(
                        selected = isSelected,
                        onClick = {
                            if(!isSelected){
                                navController.navigate(screen.screen){
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
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
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                contentWindowInsets = WindowInsets(0)
            ) { contentPadding ->
                // The navigation bar / rail already pads its own side; everything else (status bar,
                // camera cutout, system buttons on screens without the bar, keyboard) is padded here
                val barInsets = when (layoutType) {
                    NavigationSuiteType.NavigationBar ->
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                    NavigationSuiteType.NavigationRail ->
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Start)
                    else -> WindowInsets(0)
                }
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
//    width >= 1240.dp -> NavigationSuiteType.NavigationDrawer
    width >= 600.dp  -> NavigationSuiteType.NavigationRail
    else             -> NavigationSuiteType.NavigationBar
}