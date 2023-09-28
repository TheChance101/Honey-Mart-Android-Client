package org.the_chance.honeymart.ui.feature.product_details.composeable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SmallProductImages(
    modifier: Modifier = Modifier,
    onClickImage: (index: Int) -> Unit,
    state: List<String>,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        repeat(3) { index ->
            item {
                ItemImageDetailsProduction(
                    imageUrl = state.getOrNull(index) ?: "",
                    onClick = { onClickImage(index) },
                )
            }
        }
    }
}

