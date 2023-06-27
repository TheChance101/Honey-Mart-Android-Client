package org.the_chance.honeymart.ui.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import org.the_chance.honeymart.util.addOnScrollListenerWithAppbarColor
import org.the_chance.honeymart.util.addToolbarScrollListener
import org.the_chance.user.R


@Suppress("DEPRECATION")
abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    abstract val TAG: String
    abstract val layoutIdFragment: Int
    abstract val viewModel: ViewModel
    private lateinit var _binding: VB
    private lateinit var appbar: AppBarLayout
    private lateinit var imageLogoDefault: ShapeableImageView
    private lateinit var imageLogoScrolled: ShapeableImageView
    protected val binding get() = _binding

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            layoutIdFragment,
            container, false
        )

        _binding.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()

    }
    protected fun setWindowVisibility(
        appBarVisibility: Boolean = true,
        bottomNavVisibility: Boolean = true,
        isTransparentStatusBar: Boolean = true
    ) {
        val window: Window = requireActivity().window

        if (isTransparentStatusBar) {
            // make status bar transparent
        }
        else {
            window.statusBarColor = ContextCompat.getColor(
                requireContext(),
                org.the_chance.design_system.R.color.primary_100
            )
        }

        requireActivity().findViewById<AppBarLayout>(R.id.appBarLayout)?.let { appBarLayout ->
            appBarLayout.visibility = if (appBarVisibility) View.VISIBLE else View.GONE
        }

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            ?.let { bottomNavigationView ->
                bottomNavigationView.visibility =
                    if (bottomNavVisibility) View.VISIBLE else View.GONE
            }
    }


    protected fun setupScrollListenerForRecyclerView(
        recyclerView: RecyclerView,
    ) {
        appbar = requireActivity().findViewById(R.id.appBarLayout)
        imageLogoDefault = requireActivity().findViewById(R.id.image_logo)
        imageLogoScrolled = requireActivity().findViewById(R.id.image_logo_scroll)
        recyclerView.addOnScrollListenerWithAppbarColor(requireContext(), this, appbar)
        recyclerView.addToolbarScrollListener(imageLogoDefault, imageLogoScrolled)
    }

    protected open fun setup() {
    }

    override fun onResume() {
        super.onResume()
        setWindowVisibility()
    }
    protected fun log(value: Any) {
        Log.e(TAG, value.toString())
    }
}


