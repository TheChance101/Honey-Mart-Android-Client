package org.the_chance.honeymart.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.transform
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@Composable
fun <T> NavigationHandler(
    effects: SharedFlow<T>,
    handleEffect: (T, NavController) -> Unit
) {
    val navController = LocalNavigationProvider.current
    LaunchedEffect(key1 = Unit) {
        effects.throttleFirst(500).collectLatest { effect ->
            handleEffect(effect, navController)
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun <T> SharedFlow<T>.throttleFirst(windowDurationMillis: Long): Flow<T> {
    var lastEmittedTimestamp = 0L
    return transform { value ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEmittedTimestamp >= windowDurationMillis) {
            lastEmittedTimestamp = currentTime
            emit(value)
        }
    }
}