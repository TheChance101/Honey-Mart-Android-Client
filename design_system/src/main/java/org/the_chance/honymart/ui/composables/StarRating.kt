package org.the_chance.honymart.ui.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    totalStars: Int = 5,
    activeColor: Color = Color(0xFFFFD700),
    inactiveColor: Color = Color(0xFFA2ADB1)
) {
    val size = 24.dp
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        for (i in 1..totalStars) {
            Box(modifier = modifier
                .width(size)
                .height(size)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_round_star_24),
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
                        painter = painterResource(id = R.drawable.ic_round_star_24),
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
    RatingBar(rating = 3.3f, totalStars = 5)
}
