package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.the_chance.honymart.ui.theme.Shapes
import org.the_chance.honymart.ui.theme.black16
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.design_system.R

@Composable
fun HomeHorizontalItems(
    name: String,
    image: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .clip(shape = RoundedCornerShape(MaterialTheme.dimens.space12))
    ) {
        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = null,
            modifier = Modifier
                .clip(shape = Shapes.medium)
                .background(black16)
                .height(MaterialTheme.dimens.card)
        )
        Text(text = name, modifier = Modifier.align(Alignment.Center))
    }
}