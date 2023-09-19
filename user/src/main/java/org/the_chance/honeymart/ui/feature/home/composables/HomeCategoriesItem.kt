package org.the_chance.honeymart.ui.feature.home.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun HomeCategoriesItem(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    width: Dp = MaterialTheme.dimens.widthItemMarketCard,
    backgroundColor: Color = MaterialTheme.colorScheme.onTertiary,
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() }
            )
            .size(width)
            .drawBehind {
                drawIntoCanvas {
                    val path = hexagonPath(size, center)
                    it.drawOutline(
                        outline = Outline.Generic(path),
                        paint = Paint().apply {
                            color = backgroundColor
                            pathEffect = PathEffect.cornerPathEffect(16.dp.toPx())
                        }
                    )
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Icon(
            painter = painter ?: painterResource(id = R.drawable.ic_cup_paper),
            contentDescription = null,
            tint = colors.primary
        )
        Text(
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.space4)
                .width(MaterialTheme.dimens.space100),
            text = label,
            style = Typography.displaySmall.copy(black60),
            maxLines = 1,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}


private fun hexagonPath(size: Size, center: Offset): Path {

    val hexagonSize = size.maxDimension

    return Path().apply {
        val angleRadians = Math.toRadians(60.0).toFloat()
        val radius = hexagonSize / 1.7f

        (0..5).forEach { i ->
            val currentAngle = angleRadians * i
            val x = center.x + radius * cos(currentAngle)
            val y = center.y + radius * sin(currentAngle)
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }
}

@Preview
@Composable
fun PreviewHomeCategoriesItem() {
    HomeCategoriesItem(
        onClick = {},
        label = "Coffee",
    )
}
