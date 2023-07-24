package org.the_chance.honymart.ui.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


fun Modifier.overlayBottomToTop(): Modifier {
    return then(drawWithCache {
        onDrawWithContent {
            drawContent()
            drawRect(
                Brush.verticalGradient(
                    0F to Color.Transparent,
                    0.5F to Color.Transparent,
                    0.9f to Color(0x99000000),
                ),
            )
        }
    })
}