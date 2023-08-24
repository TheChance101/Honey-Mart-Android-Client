package org.the_chance.honeymart.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.the_chance.design_system.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentVisibility(
    state: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(animationSpec = tween(durationMillis = 500)) + scaleIn(),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        if (state)
            content()
        else {
            ShowEmptyPlaceholder(
                state = !state,
                title = stringResource(R.string.empty_coupons),
                description = stringResource(R.string.there_is_no_coupons_here),
            )
        }
    }
}