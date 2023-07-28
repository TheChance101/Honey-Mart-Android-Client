package org.the_chance.honeymart.ui.feature.product_details.composeable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens


@Composable
fun SmallProductImages(
    state: List<String>,
    modifier: Modifier = Modifier,
    onClickImage: (index: Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {

        items(
            count = state.size,
        ) { index ->
            ItemImageDetailsProduction(
                state[index],
                modifier = Modifier
                    .clickable(onClick = {
                        onClickImage(index)
                    })
            )
        }

    }
}

@Preview
@Composable
fun SmallProductImagesPreview() {
    HoneyMartTheme {
        SmallProductImages(
            state = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/1/13/Supermarkt.jpg",
                "https://m.media-amazon.com/images/I/51mmrjhqOqL._AC_UF1000,1000_QL80_DpWeblab_.jpg"
            )
        ) {}
    }
}