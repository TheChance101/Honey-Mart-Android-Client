package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun HorizontalPagerIndicator(
    itemCount: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until itemCount) {
            PagerIndicatorItem(selected = i == selectedPage)
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.space8))
        }
    }
}

@Composable
fun PagerIndicatorItem(selected: Boolean) {
    val color =
        if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.outlineVariant
    val width = if (selected) MaterialTheme.dimens.space20 else MaterialTheme.dimens.space12
    val height = MaterialTheme.dimens.space6
    val cornerRadius = MaterialTheme.dimens.space4

    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .background(color, shape = RoundedCornerShape(cornerRadius))
    )
}