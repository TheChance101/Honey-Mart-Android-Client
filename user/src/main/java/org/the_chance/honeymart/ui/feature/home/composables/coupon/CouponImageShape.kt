package org.the_chance.honeymart.ui.feature.home.composables.coupon

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection


val CouponImageShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            couponImageShape(
                size = size,
                cornerRadius = 24f,
                smallCornerRadius = 4f,
                mediumCornerRadius = 8f
            )
        )
    }
}


fun couponImageShape(
    size: Size,
    cornerRadius: Float,
    smallCornerRadius: Float,
    mediumCornerRadius: Float,
): Path {
    return Path().apply {
        reset()

        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = -cornerRadius,
                right = cornerRadius,
                bottom = cornerRadius
            ),
            startAngleDegrees = 90.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = size.width, y = 0f)

        var centerYEnd = smallCornerRadius + mediumCornerRadius


        while (centerYEnd < size.height) {
            arcTo(
                rect = Rect(
                    left = size.width - mediumCornerRadius,
                    top = centerYEnd - mediumCornerRadius,
                    right = size.width + mediumCornerRadius,
                    bottom = centerYEnd + mediumCornerRadius
                ),
                startAngleDegrees = -90.0f,
                sweepAngleDegrees = -180.0f,
                forceMoveTo = false
            )
            centerYEnd += 2 * mediumCornerRadius + mediumCornerRadius
        }

        lineTo(x = size.width, y = size.height)

        arcTo(
            rect = Rect(
                left = -cornerRadius,
                top = size.height - cornerRadius,
                right = cornerRadius,
                bottom = size.height + cornerRadius
            ),
            startAngleDegrees = 0.0f,
            sweepAngleDegrees = -90.0f,
            forceMoveTo = false
        )

        lineTo(x = 0f, y = cornerRadius)

        var centerYStart = size.height - cornerRadius

        while (centerYStart > cornerRadius) {
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
