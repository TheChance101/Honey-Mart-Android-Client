package org.the_chance.honeymart.ui.features.coupons.composables.coupon

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

class CouponImageShape(
    private val middleNotchRadius: Dp,
    private val sideNotchGap: Dp,
    private val sideNotchRadius: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            couponImageShape(
                size = size,
                middleNotchRadius = CornerSize(middleNotchRadius).toPx(size, density),
                sideNotchGap = CornerSize(sideNotchGap).toPx(size, density),
                sideNotchRadius = CornerSize(sideNotchRadius).toPx(size, density)
            )
        )
    }
}

private fun couponImageShape(
    size: Size,
    middleNotchRadius: Float,
    sideNotchGap: Float,
    sideNotchRadius: Float,
): Path {
    return Path().apply {
        reset()

        arcTo(
            rect = Rect(
                left = -middleNotchRadius,
                top = -middleNotchRadius,
                right = middleNotchRadius,
                bottom = middleNotchRadius
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width, y = 0f)

        var centerYEnd = sideNotchGap + sideNotchRadius


        while (centerYEnd < size.height) {
            arcTo(
                rect = Rect(
                    left = size.width - sideNotchRadius,
                    top = centerYEnd - sideNotchRadius,
                    right = size.width + sideNotchRadius,
                    bottom = centerYEnd + sideNotchRadius
                ),
                startAngleDegrees = -90.0f,
                sweepAngleDegrees = -180.0f,
                forceMoveTo = false
            )
            centerYEnd += 2 * sideNotchRadius + sideNotchRadius
        }

        lineTo(x = size.width, y = size.height)

        arcTo(
            rect = Rect(
                left = -middleNotchRadius,
                top = size.height - middleNotchRadius,
                right = middleNotchRadius,
                bottom = size.height + middleNotchRadius
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = 0f, y = middleNotchRadius)

        var centerYStart = size.height - middleNotchRadius

        while (centerYStart >= middleNotchRadius + middleNotchRadius) {
            addRect(
                rect = Rect(
                    left = -2f,
                    top = centerYStart - 8f - 4f,
                    right = 2f,
                    bottom = centerYStart - 8f + 2f
                )
            )
            centerYStart -= 8f + 8f
        }

        close()
    }
}
