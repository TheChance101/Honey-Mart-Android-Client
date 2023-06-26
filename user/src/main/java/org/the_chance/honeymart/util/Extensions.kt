package org.the_chance.honeymart.util

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.the_chance.design_system.R

fun RecyclerView.addOnScrollListenerWithAppbarColor(
    context: Context,
    fragment: Fragment,
    appBarLayout: AppBarLayout,
) {
    val threshold =
        context.resources.getDimensionPixelSize(org.the_chance.design_system.R.dimen.spacing_8)
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
                ContextCompat.getColor(context, R.color.white_300),
                ContextCompat.getColor(context, R.color.primary_100),
                alpha
            )

            appBarLayout.setBackgroundColor(newColor)
            window.statusBarColor = newColor

        }
    })
}

fun RecyclerView.addToolbarScrollListener(
    imageLogoDefault: ShapeableImageView,
    imageLogoScrolled: ShapeableImageView,
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

fun <T> LifecycleOwner.collect(flow: Flow<T>, action: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                action.invoke(it)
            }
        }
    }
}


fun Fragment.showSnackBar(
    message: String,
    actionText: String? = null,
    action: (() -> Unit)? = null,
) {
    val rootView = requireView()
    val snackBar = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)

    val bottomNavBar =
        requireActivity().findViewById<BottomNavigationView>(org.the_chance.user.R.id.bottomNavigationView)
    snackBar.anchorView = bottomNavBar

    if (actionText != null && action != null) {
        snackBar.setAction(actionText) { action.invoke() }
    }

    snackBar.show()
}

fun Fragment.showExitAlertDialog() {
    MaterialAlertDialogBuilder(requireContext(), R.style.alertDialogCustomStyle)
        .setTitle(getString(R.string.dialog_title))
        .setMessage(getString(R.string.confirm))
        .setPositiveButton(
            getString(R.string.exit)
        ) { _, _ ->
            activity?.moveTaskToBack(true)
            activity?.finish()
        }
        .setNegativeButton(
            getString(R.string.stay)
        ) { _, _ ->
            /*Snackbar.make(requireView(), getString(R.string.canceled), Snackbar.LENGTH_SHORT)
                .show()*/
            //TODO
        }.show()
}
