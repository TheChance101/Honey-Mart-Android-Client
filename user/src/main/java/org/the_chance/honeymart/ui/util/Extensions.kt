package org.the_chance.honeymart.ui.util

import android.content.Context
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView

fun RecyclerView.addOnScrollListenerWithAppbarColor(
    context: Context,
    fragment: Fragment,
    appBarLayout: AppBarLayout,
) {
    val threshold = context.resources.getDimensionPixelSize(org.the_chance.design_system.R.dimen.spacing_medium)
    val window: Window = fragment.requireActivity().window

    fun interpolateColor(color1: Int, color2: Int, ratio: Float): Int {
        val interpolatedColor = ArgbEvaluatorCompat.getInstance().evaluate(ratio, color1, color2)
        return interpolatedColor.coerceAtLeast(color1).coerceAtMost(color2)
    }

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
            window.statusBarColor = newColor

        }
    })
}
fun RecyclerView.addToolbarScrollListener(
    imageLogoDefault: ShapeableImageView,
    imageLogoScrolled: ShapeableImageView
) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private var isAtTop = true

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val isVerticalScroll = dy != 0
            if (isVerticalScroll) {
                val scrollY = recyclerView.computeVerticalScrollOffset()
                if (scrollY == 0) {
                    if (!isAtTop) {
                        imageLogoDefault.visibility = View.VISIBLE
                        imageLogoScrolled.visibility = View.GONE
                        isAtTop = true
                    }
                } else {
                    if (isAtTop) {
                        imageLogoDefault.visibility = View.GONE
                        imageLogoScrolled.visibility = View.VISIBLE
                        isAtTop = false
                    }
                }
            }
        }
    })
}

