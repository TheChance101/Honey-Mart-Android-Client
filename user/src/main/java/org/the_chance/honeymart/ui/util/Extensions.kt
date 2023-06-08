package org.the_chance.honeymart.ui.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import org.the_chance.design_system.R

fun RecyclerView.addOnScrollListenerWithAppbarColor(
    context: Context,
    fragment: Fragment,
    appBarLayout: AppBarLayout,
) {
    val threshold =
        context.resources.getDimensionPixelSize(R.dimen.spacing_medium)
    val window: Window = fragment.requireActivity().window

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val offset = recyclerView.computeVerticalScrollOffset()
            val alpha = (offset.toFloat() / threshold).coerceIn(0f, 1f)
            val newColor = interpolateColor(
                context,
                R.color.white_300,
                R.color.primary_100,
                alpha
            )
            appBarLayout.setBackgroundColor(newColor)
            window.statusBarColor = newColor
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

            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    imageDefault.visibility = View.GONE
                    imageScrolled.visibility = View.VISIBLE
                }

                RecyclerView.SCROLL_STATE_IDLE -> {
                    // Scrolling has stopped
                    if (!recyclerView.canScrollVertically(-1)) {
                        // Reached the top of the RecyclerView
                        imageDefault.visibility = View.VISIBLE
                        imageScrolled.visibility = View.GONE
                    }
                }
            }
        }
    })
}

private fun interpolateColor(context: Context, colorRes1: Int, colorRes2: Int, ratio: Float): Int {
    val color1 = ContextCompat.getColor(context, colorRes1)
    val color2 = ContextCompat.getColor(context, colorRes2)
    val r = (Color.red(color1) * (1 - ratio) + Color.red(color2) * ratio).toInt()
    val g = (Color.green(color1) * (1 - ratio) + Color.green(color2) * ratio).toInt()
    val b = (Color.blue(color1) * (1 - ratio) + Color.blue(color2) * ratio).toInt()
    return Color.rgb(r, g, b)
}
