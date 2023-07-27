package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.honymart.ui.theme.dimens
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun HexagonItem(icon: Int, hexagonSize: Dp, isSelected: Boolean, onClick: () -> Unit) {
    val hexagonItemShape = object : Shape {
        override fun createOutline(
            size: Size,
            layoutDirection: LayoutDirection,
            density: Density
        ): Outline {
            val minSize = min(size.width, size.height)
            val angleRadians = Math.toRadians(60.0).toFloat()
            val radius = minSize / 2f
            return Outline.Generic(
                Path().apply {
                    (0..5).forEach { i ->
                        val currentAngle = angleRadians * i
                        val x = radius + radius * cos(currentAngle)
                        val y = radius + radius * sin(currentAngle)
                        if (i == 0) moveTo(x, y) else lineTo(x, y)
                    }
                    close()
                }
            )
        }
    }
    HoneyMartTheme {
        Box(
            modifier = Modifier
                .size(hexagonSize)
                .clip(hexagonItemShape)
                .background(
                    if (isSelected) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(bottom = MaterialTheme.dimens.space4)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "category item",
                tint = if (isSelected) White else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }


}