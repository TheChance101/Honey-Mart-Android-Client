package org.the_chance.honeymart.ui.feature.home.composables.coupon

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

val CouponDetailsShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            couponDetailsPath(
                size = size,
                cornerRadius = 24f
            )
        )
    }
}

fun couponDetailsPath(size: Size, cornerRadius: Float): Path {
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

        lineTo(x = size.width - cornerRadius, y = 0f)

        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = -cornerRadius,
                right = size.width + cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width, y = size.height - cornerRadius)

        arcTo(
            rect = Rect(
                left = size.width - cornerRadius,
                top = size.height - cornerRadius,
                right = size.width + cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 270.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = cornerRadius, y = size.height)

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
