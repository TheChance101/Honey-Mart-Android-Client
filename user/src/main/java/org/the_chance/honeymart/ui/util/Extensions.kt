package org.the_chance.honeymart.ui.util

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView

fun RecyclerView.addOnScrollListenerWithAppbarColor(context: Context, appBarLayout: AppBarLayout) {
    val threshold =
        context.resources.getDimensionPixelSize(org.the_chance.design_system.R.dimen.spacing_medium)

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

fun RecyclerView.addOnScrollListenerWithImageVisibility(
    imageDefault: ShapeableImageView,
    imageScrolled: ShapeableImageView
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)


            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                // Scrolling is in progress
                imageDefault.visibility = View.GONE
                imageScrolled.visibility = View.VISIBLE
            } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                // Scrolling has stopped
                if (!recyclerView.canScrollVertically(-1)) {
                    // Reached the top of the RecyclerView
                    imageDefault.visibility = View.VISIBLE
                    imageScrolled.visibility = View.GONE
                }
            }


        }
    })
}

private fun interpolateColor(color1: Int, color2: Int, ratio: Float): Int {
    val r = (Color.red(color1) * (1 - ratio) + Color.red(color2) * ratio).toInt()
    val g = (Color.green(color1) * (1 - ratio) + Color.green(color2) * ratio).toInt()
    val b = (Color.blue(color1) * (1 - ratio) + Color.blue(color2) * ratio).toInt()
    return Color.rgb(r, g, b)
}
