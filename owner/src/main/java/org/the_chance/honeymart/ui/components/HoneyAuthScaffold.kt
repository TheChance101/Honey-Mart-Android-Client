package org.the_chance.honeymart.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import org.the_chance.honymart.ui.composables.HoneyAppBarTitle
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R

/**
 * Created by Aziza Helmy on 8/5/2023.
 */
@Composable
fun HoneyAuthScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.image_group),
            contentDescription = "",
            modifier = Modifier
                .size(MaterialTheme.dimens.sunImageSize)
                .align(TopEnd),
            contentScale = ContentScale.FillBounds
        )

        Row {
            Image(
                painter = painterResource(id = org.the_chance.owner.R.drawable.auth_image),
                contentDescription = "authorization background image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = MaterialTheme.dimens.space32),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                content()
            }
        }

        HoneyAppBarTitle(
            modifier = Modifier.padding(
                start = MaterialTheme.dimens.space16,
                top = MaterialTheme.dimens.space32
            ),
            titleColor = Color.White
        )
    }
}