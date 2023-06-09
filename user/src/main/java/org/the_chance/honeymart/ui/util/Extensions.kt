package org.the_chance.honeymart.ui.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import org.the_chance.design_system.R

fun RecyclerView.addOnScrollListenerWithAppbarColor(
    context: Context,
    fragment: Fragment,
    appBarLayout: AppBarLayout,
) {
    val threshold = context.resources.getDimensionPixelSize(R.dimen.spacing_medium)
    val window: Window = fragment.requireActivity().window
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val offset = recyclerView.computeVerticalScrollOffset()
            val alpha = (offset.toFloat() / threshold).coerceIn(0f, 1f)
            val newCollorScrolled = interpolateColor(
                ContextCompat.getColor(context, R.color.white_300),
                ContextCompat.getColor(context, R.color.primary_100),
                alpha
            )
            appBarLayout.setBackgroundColor(newCollorScrolled)
            window.statusBarColor = newCollorScrolled

            // this animate toolbar
/*            animateBackgroundColor(appBarLayout, newCollorScrolled)
            animateStatusBarColor(window, newCollorScrolled)*/
        }
    })
}

private fun animateBackgroundColor(appBarLayout: AppBarLayout, newColor: Int) {
    val currentColor = (appBarLayout.background as? ColorDrawable)?.color ?: 0
    val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), currentColor, newColor)
    colorAnimator.duration = 10
    colorAnimator.addUpdateListener { animator ->
        val animatedValue = animator.animatedValue as Int
        appBarLayout.setBackgroundColor(animatedValue)
    }
    colorAnimator.start()
}

private fun animateStatusBarColor(window: Window, newColor: Int) {
    val currentColor = window.statusBarColor
    val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), currentColor, newColor)
    colorAnimator.duration = 10
    colorAnimator.addUpdateListener { animator ->
        val animatedValue = animator.animatedValue as Int
        window.statusBarColor = animatedValue
    }
    colorAnimator.start()
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
                    if (!recyclerView.canScrollVertically(-1)) {
                        imageDefault.visibility = View.VISIBLE
                        imageScrolled.visibility = View.GONE
                    }
                }
            }
        }
    })
}

private fun interpolateColor(color1: Int, color2: Int, ratio: Float): Int {
    val interpolatedColor = ArgbEvaluatorCompat.getInstance().evaluate(ratio, color1, color2)
    return interpolatedColor.coerceAtLeast(color1).coerceAtMost(color2)
}