package org.the_chance.honeymart.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import org.the_chance.honeymart.ui.navigation.UserNavGraph

@Composable
fun UserNavigationApp() {
    CompositionLocalProvider(
        LocalNavigationProvider provides rememberNavController()
    ) {
        UserNavGraph()
    }
}