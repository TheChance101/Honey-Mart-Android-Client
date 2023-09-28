package org.the_chance.honeymart.ui.feature.marketInfo.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.the_chance.honeymart.ui.feature.marketInfo.CategoryUiState
import org.the_chance.honymart.ui.theme.dimens
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun HexagonItem(
    state: CategoryUiState,
    onClickCategory: (categoryId: Long, position: Int) -> Unit,
    position: Int,
    hexagonSize: Dp = 140.dp,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (position % 2 == 0) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.inverseSurface
    }
    Box(
        modifier = modifier
            .offset(y = if (position % 3 == 1) (hexagonSize / 2) - 0.dp else 0.dp)
            .padding(start = if (position % 3 == 0) 0.dp - 0.dp else 0.dp)
            .padding(end = if (position % 3 == 2) 0.dp else 0.dp)
            .offset(x = if (position % 3 == 0) 4.dp else 0.dp)
            .offset(x = if (position % 3 == 2) (-4).dp else 0.dp)
            .size(hexagonSize)
            .scale(1.19f)
            .clip(HexagonItemShape)
            .background(backgroundColor)
            .clickable { onClickCategory(state.categoryId, position) },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = state.categoryName,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = MaterialTheme.dimens.space12)
        )
    }
}

val HexagonItemShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: Density
    ): Outline {
        val minSize = min(size.width, size.height)
        val angleRadians = Math.toRadians(60.0).toFloat()
        val radius = minSize / 2f
        val path = Path().apply {
            (0..5).forEach { i ->
                val currentAngle = angleRadians * i
                val x = radius + radius * cos(currentAngle)
                val y = radius + radius * sin(currentAngle)
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }
        return Outline.Generic(path)
    }
}