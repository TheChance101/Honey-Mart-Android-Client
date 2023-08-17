package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.white
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun Hexagon(
    imageUrl: String = "",
    label: String = "",
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        HexagonShapeCanvas()
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            ImageNetwork(
                imageUrl = imageUrl,
                contentDescription = "image",
                modifier = Modifier.size(32.dp)
            )
            Text(text = label,
                style = Typography.displaySmall.copy(black60))
        }
    }
}

@Composable
fun HexagonShapeCanvas() {
    Canvas(
        modifier = Modifier
            .size(height = 120.dp, width = 130.dp)
    ) {
        val hexagonSize = size.maxDimension
        val hexagonPath = Path().apply {
            val angleRadians = Math.toRadians(60.0).toFloat()
            val radius = hexagonSize / 2f

            (0..5).forEach { i ->
                val currentAngle = angleRadians * i
                val x = center.x + radius * cos(currentAngle)
                val y = center.y + radius * sin(currentAngle)
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()

        }
        drawIntoCanvas {
            it.drawOutline(
                outline = Outline.Generic(hexagonPath),
                paint = Paint().apply {
                    color = white
                    pathEffect = PathEffect.cornerPathEffect(16.dp.toPx())
                }
            )
        }
    }
}
