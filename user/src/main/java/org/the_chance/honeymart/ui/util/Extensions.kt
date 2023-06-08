package org.the_chance.honeymart.ui.util

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout

fun RecyclerView.addOnScrollListenerWithAppbarColor(context: Context, appBarLayout: AppBarLayout) {
    val threshold = context.resources.getDimensionPixelSize(org.the_chance.design_system.R.dimen.spacing_medium)

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val offset = recyclerView.computeVerticalScrollOffset()
            val alpha = (offset.toFloat() / threshold).coerceIn(0f, 1f)
            val newColor = interpolateColor(
                ContextCompat.getColor(context, org.the_chance.design_system.R.color.white_300),
                ContextCompat.getColor(context, org.the_chance.design_system.R.color.primary_100),
                alpha
            )
            appBarLayout.setBackgroundColor(newColor)
        }
    })
}

private fun interpolateColor(color1: Int, color2: Int, ratio: Float): Int {
    val r = (Color.red(color1) * (1 - ratio) + Color.red(color2) * ratio).toInt()
    val g = (Color.green(color1) * (1 - ratio) + Color.green(color2) * ratio).toInt()
    val b = (Color.blue(color1) * (1 - ratio) + Color.blue(color2) * ratio).toInt()
    return Color.rgb(r, g, b)
}
