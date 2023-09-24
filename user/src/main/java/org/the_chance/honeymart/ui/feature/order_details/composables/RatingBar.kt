package org.the_chance.honeymart.ui.feature.order_details.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.lightGrey

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    size: Dp,
    onRatingChanged: ((Float) -> Unit),
    totalStars: Int = 5,
    activeColor: Color = MaterialTheme.colorScheme.primary,
    inactiveColor: Color = lightGrey,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..totalStars) {
            Box(modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .clickable {
                    onRatingChanged.invoke(i.toFloat())
                }
                .padding(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.star_7),
                    contentDescription = "star",
                    modifier = Modifier.size(size),
                    tint = inactiveColor
                )
                val clipFraction = when {
                    i <= rating -> 1f
                    i - 1 < rating && i > rating -> rating - i + 1
                    else -> 0f
                }
                if (clipFraction > 0) {
                    Icon(
                        painter = painterResource(id = R.drawable.star_7),
                        contentDescription = "star",
                        modifier = Modifier
                            .size(size)
                            .clipFraction(clipFraction),
                        tint = activeColor
                    )
                }
            }
        }
    }
}

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clipFraction(fraction: Float): Modifier = composed {
    this.then(
        object : DrawModifier {
            override fun ContentDrawScope.draw() {
                val contentToDraw = this::drawContent
                clipRect(
                    left = 0f,
                    top = 0f,
                    right = size.width * fraction,
                    bottom = size.height
                ) {
                    contentToDraw()
                }
            }
        }
    )
}