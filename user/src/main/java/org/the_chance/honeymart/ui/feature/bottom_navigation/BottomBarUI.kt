package org.the_chance.honeymart.ui.feature.bottom_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.white
import org.the_chance.design_system.R

@Composable
fun BottomBarUi(bottomNavState: MutableState<Boolean>) {
    val navController = LocalNavigationProvider.current

    val screens = listOf(
        BottomBarItems.Home,
        BottomBarItems.Cart,
        BottomBarItems.Order,
        BottomBarItems.WishList,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedVisibility(
        visible = bottomNavState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.onTertiary,
            ) {
                screens.forEach { screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                }
            }
        }
    )
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarItems,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected =
        currentDestination?.hierarchy?.any { it.route == screen.route } == true
    NavigationBarItem(
        icon = {
            Icon(
                painter =
                if (selected) painterResource(id = screen.selectedIcon)
                else painterResource(id = screen.unSelectedIcon),
                contentDescription = stringResource(R.string.navigation_icon),
                tint = if (selected) white else MaterialTheme.colorScheme.outlineVariant
            )
        },
        selected = selected,
        label = { Text(text = if (selected) screen.label else "", color = MaterialTheme.colorScheme.onErrorContainer) },
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
            when (screen) {
                BottomBarItems.Home -> {
                    navController.popBackStack(Screen.HomeScreen.route, false)
                }

                BottomBarItems.Cart -> {
                    navController.popBackStack(Screen.CartScreen.route, false)
                }

                BottomBarItems.Order -> {
                    navController.popBackStack(Screen.OrderScreen.route, false)
                }

                BottomBarItems.WishList -> {
                    navController.popBackStack(Screen.WishListScreen.route, false)
                }
            }

        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = white,
            indicatorColor = MaterialTheme.colorScheme.errorContainer,
            unselectedIconColor = black60
        )
    )
}