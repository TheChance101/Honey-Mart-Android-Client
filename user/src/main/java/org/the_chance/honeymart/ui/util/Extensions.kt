package org.the_chance.honeymart.ui.util

import android.view.View
import android.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

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

