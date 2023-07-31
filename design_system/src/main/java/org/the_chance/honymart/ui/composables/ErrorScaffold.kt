package org.the_chance.honymart.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.black60

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ErrorScaffold(state: Boolean) {
    HoneyMartTheme {
        AnimatedVisibility(
            visible = state,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 500)) + slideInVertically() + scaleIn(),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 500)) + slideOutVertically() + scaleOut()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_connection_placeholder_img),
                    contentDescription = "network error",
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Oops, No connection !!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = black60
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewErrorScaffold() {
    ErrorScaffold(true)
}