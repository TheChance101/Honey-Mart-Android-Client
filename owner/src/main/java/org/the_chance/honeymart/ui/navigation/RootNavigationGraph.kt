package org.the_chance.honeymart.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

@Composable
fun RootNavigationGraph() {
    val navController = LocalNavigationProvider.current

    NavHost(
    navController = navController,
    startDestination = Graph.AUTH_GRAPH
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