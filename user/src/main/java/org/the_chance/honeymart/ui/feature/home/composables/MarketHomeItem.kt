package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens

@Preview
@Composable
fun MarketHomeItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(black60)
            .padding(horizontal = MaterialTheme.dimens.space4)
            .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space12))
            .height(MaterialTheme.dimens.card)
            .clickable(onClick = {}),

        ) {

        Image(
            painter = rememberAsyncImagePainter(model = R.drawable.background_frame),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(shape = Shapes.medium)
                .height(MaterialTheme.dimens.card),
        )
    }
}
