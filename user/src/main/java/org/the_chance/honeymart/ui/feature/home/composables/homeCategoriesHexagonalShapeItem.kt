package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Hexagon(
    size: Float,
    color: Color
) {
    Canvas(
        modifier = androidx.compose.ui.Modifier.size(size.dp)
    ) {
        val hexagonPath = Path()
        val centerX = size / 2
        val centerY = size / 2
        val radius = size / 2
        val angle = Math.PI / 3

        hexagonPath.moveTo(centerX + radius * cos(0.0).toFloat(), centerY + radius * Math.sin(0.0).toFloat())

        for (i in 1..6) {
            val x = centerX + radius * cos(angle * i).toFloat()
            val y = centerY + radius * sin(angle * i).toFloat()
            hexagonPath.lineTo(x, y)
        }

        hexagonPath.close()

        drawPath(
            path = hexagonPath,
            color = color
        )
    }
}

@Preview
@Composable
fun HexagonPreview() {
    Hexagon(size = 138f, color = Color.Blue)
}
