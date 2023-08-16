package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.primary100

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
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
@Composable
fun PagerIndicatorItem(selected: Boolean) {
    val color = if (selected) primary100 else black37
    val width = if (selected) 20.dp else 10.dp
    val height = 6.dp
    val cornerRadius = 3.dp

    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .background(color, shape = RoundedCornerShape(cornerRadius))
    )
}