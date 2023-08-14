package org.the_chance.honeymart.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun PlaceHolderItem(
    visibility: Boolean,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    imagePainter: Painter = painterResource(id = R.drawable.placeholder_wish_list),
) {
    HoneyMartTheme {
        AnimatedVisibility(
            visible = visibility,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut(animationSpec = tween(durationMillis = 500))
        ) {
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = stringResource(R.string.placeholder_image),
                    contentScale = ContentScale.Crop
                )
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space32),
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.dimens.space16),
                    text = subtitle,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlaceHolderItem() {
    PlaceHolderItem(
        visibility = true,
        title = "Your Products is empty!!",
        subtitle = "Adding a product will increase your chances of attracting interested buyers. What product fits your item?"
    )
}