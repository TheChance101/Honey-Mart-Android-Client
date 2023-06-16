package org.the_chance.honeymart.util

import android.animation.ArgbEvaluator
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun Activity.setupScrollingAppBar(
    appBarLayout: AppBarLayout,
    logoImageView: ShapeableImageView,
    colorStart: Int,
    colorEnd: Int,
    logoStart: Int,
    logoEnd: Int
) {
    appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        val scrollRange = appBarLayout.totalScrollRange
        val scrollPercentage = Math.abs(verticalOffset) / scrollRange.toFloat()

        // Change the app bar color based on scroll percentage
        val color = ArgbEvaluator().evaluate(scrollPercentage, colorStart, colorEnd)
        appBarLayout.setBackgroundColor(color as Int)

        // Change the app bar logo based on scroll percentage
        val logo = if (scrollPercentage >= 0.5f) logoEnd else logoStart
        logoImageView.setImageResource(logo)
    })
}


//fun RecyclerView.addOnScrollListenerWithAppbarColor(
//    context: Context,
//    fragment: Fragment,
//    appBarLayout: AppBarLayout,
//) {
//    val threshold =
//        context.resources.getDimensionPixelSize(org.the_chance.design_system.R.dimen.spacing_8)
//    val window: Window = fragment.requireActivity().window
//
//    addOnScrollListener(object : RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//
//            val offset = recyclerView.computeVerticalScrollOffset()
//            val alpha = (offset.toFloat() / threshold).coerceIn(0f, 1f)
//            val newColor = interpolateColor(
//                ContextCompat.getColor(context, org.the_chance.design_system.R.color.white_300),
//                ContextCompat.getColor(context, org.the_chance.design_system.R.color.primary_100),
//                alpha
//            )
//
//            appBarLayout.setBackgroundColor(newColor)
//            window.statusBarColor = newColor
//
//        }
//    })
//}
//
//
//fun RecyclerView.addOnScrollListenerWithImageVisibility(
//    imageDefault: ShapeableImageView,
//    imageScrolled: ShapeableImageView
//) {
//    addOnScrollListener(object : RecyclerView.OnScrollListener() {
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//
//            when (newState) {
//                RecyclerView.SCROLL_STATE_DRAGGING -> {
//                    imageDefault.visibility = View.GONE
//                    imageScrolled.visibility = View.VISIBLE
//                }
//
//                RecyclerView.SCROLL_STATE_IDLE -> {
//                    if (!recyclerView.canScrollVertically(-1)) {
//                        imageDefault.visibility = View.VISIBLE
//                        imageScrolled.visibility = View.GONE
//                    }
//                }
//            }
//        }
//    })
//}
//
//private fun interpolateColor(color1: Int, color2: Int, ratio: Float): Int {
//    val interpolatedColor = ArgbEvaluatorCompat.getInstance().evaluate(ratio, color1, color2)
//    return interpolatedColor.coerceAtLeast(color1).coerceAtMost(color2)
//}

fun <T> LifecycleOwner.collect(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                action.invoke(it)
            }
        }
    }
}