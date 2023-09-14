package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.lightGrey
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    totalStars: Int = 5,
    activeColor: Color = primary100,
    inactiveColor: Color = lightGrey
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..totalStars) {
            Box(modifier = modifier.size(24.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.star_7),
                    contentDescription = "star",
                    modifier = Modifier.matchParentSize(),
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
                            .matchParentSize()
                            .clipFraction(clipFraction),
                        tint = activeColor
                    )
                }
            }
        }
    }
}

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

@Preview()
@Composable
fun RatingBarPreview() {
    RatingBar(rating = 4.4f, totalStars = 5)
}