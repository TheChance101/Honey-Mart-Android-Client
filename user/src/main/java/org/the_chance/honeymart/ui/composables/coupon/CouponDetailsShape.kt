package org.the_chance.honeymart.ui.composables.coupon
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

class CouponDetailsShape(
    private val cornerRadius: Dp,
    private val notchRadius: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            couponDetailsPath(
                size = size,
                cornerRadius = CornerSize(cornerRadius).toPx(size, density),
                notchRadius = CornerSize(notchRadius).toPx(size, density)
            )
        )
    }
}

private fun couponDetailsPath(size: Size, cornerRadius: Float, notchRadius: Float): Path {
    return Path().apply {
        reset()
        arcTo(
            rect = Rect(
                offset = Offset.Zero,
                size = Size(cornerRadius * 2, cornerRadius * 2)
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width - notchRadius, y = 0f)

        arcTo(
            rect = Rect(
                left = size.width - notchRadius,
                top = -notchRadius,
                right = size.width + notchRadius,
                bottom = notchRadius
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width, y = size.height - notchRadius)

        arcTo(
            rect = Rect(
                left = size.width - notchRadius,
                top = size.height - notchRadius,
                right = size.width + notchRadius,
                bottom = size.height + notchRadius
            ),
            startAngleDegrees = 270.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = notchRadius, y = size.height)

        arcTo(
            rect = Rect(
                offset = Offset(0f, size.height - cornerRadius * 2),
                size = Size(cornerRadius * 2, cornerRadius * 2)
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )

        lineTo(x = 0f, y = cornerRadius)

        close()
    }
}
