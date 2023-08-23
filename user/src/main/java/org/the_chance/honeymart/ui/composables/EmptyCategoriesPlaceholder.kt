package org.the_chance.honeymart.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EmptyCategoriesPlaceholder(state: Boolean) {
    HoneyMartTheme {
        AnimatedVisibility(
            visible = state,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 500)
            ) + scaleIn(),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.placeholder_order),
                    contentDescription = stringResource(R.string.empty_product),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space56),
                    text = stringResource(R.string.there_is_no_categories_here),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Preview
@Composable
fun PreviewEmptyCategoriesScaffold() {
    EmptyCategoriesPlaceholder(true)
}