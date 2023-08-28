package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import org.the_chance.honeymart.LocalNavigationProvider

@Composable
fun RootNavigationGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
    navController = navController,
    startDestination = Graph.MAIN_GRAPH
    )
    {
        authNavGraph()
        mainNavGraph()
    }
}

object Graph {
    const val AUTH_GRAPH = "auth_graph"
    const val MAIN_GRAPH = "main_graph"
}