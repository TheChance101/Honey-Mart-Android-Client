package org.the_chance.honeymart.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable

@Composable
fun ContentVisibility(
    state: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
){
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInHorizontally(),
        exit = fadeOut(animationSpec = tween(durationMillis = 500)) + slideOutVertically()
    ) {
        content()
    }
}