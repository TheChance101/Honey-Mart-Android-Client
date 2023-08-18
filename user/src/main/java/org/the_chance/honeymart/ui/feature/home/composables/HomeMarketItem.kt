package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun HomeHorizontalItems(
    name: String,
    image: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.size(MaterialTheme.dimens.card)
            .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space12))
    ) {
        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                black37,
                blendMode = BlendMode.Multiply
            ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = name, modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Preview
@Composable
fun HomeHorizontalItemsPreview() {
    HoneyMartTheme {
        HomeHorizontalItems(
            name = "Test",
            image = "Test"
        )
    }
}