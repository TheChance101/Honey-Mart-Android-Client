package org.the_chance.honeymart.ui.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import org.the_chance.honeymart.ui.util.addOnScrollListenerWithAppbarColor
import org.the_chance.honeymart.ui.util.addToolbarScrollListener
import org.the_chance.user.R


abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    abstract val TAG: String
    abstract val layoutIdFragment: Int
    abstract val viewModel: ViewModel
    private lateinit var _binding: VB
    private lateinit var appbar: AppBarLayout
    private lateinit var imageLogoDefault: ShapeableImageView
    private lateinit var imageLogoScrolled: ShapeableImageView
    protected val binding get() = _binding

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setStatusBarMode(requireActivity())

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

    protected open fun setup() {}

    protected fun log(value: Any) {
        Log.e(TAG, value.toString())
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

    @SuppressLint("InlinedApi")
    private fun setStatusBarMode(activity: Activity) {
        val window = activity.window
        val decorView = window.decorView

        window.apply {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                statusBarColor = Color.TRANSPARENT
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }


}


