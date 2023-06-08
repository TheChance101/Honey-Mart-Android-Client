package org.the_chance.honeymart.ui.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import org.the_chance.honeymart.ui.util.addOnScrollListenerWithAppbarColor
import org.the_chance.honeymart.ui.util.addOnScrollListenerWithImageVisibility
import org.the_chance.user.R


abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    abstract val TAG: String
    abstract val layoutIdFragment: Int
    abstract val viewModel: ViewModel
    private lateinit var _binding: VB
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var imageLogoDefault: ShapeableImageView
    private lateinit var imageLogoScrolled: ShapeableImageView
    protected val binding get() = _binding

    @SuppressLint("ResourceType")
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
        val window = requireActivity().window
        window.decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        )
        window.statusBarColor =
            ContextCompat.getColor(requireContext(), android.R.color.transparent)


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

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun setupScrollListenerForRecyclerView(
        recyclerView: RecyclerView,
    ) {
        appBarLayout = requireActivity().findViewById(R.id.appBarLayout)
        imageLogoDefault = requireActivity().findViewById(R.id.image_logo)
        imageLogoScrolled = requireActivity().findViewById(R.id.image_logo_scroll)
        recyclerView.addOnScrollListenerWithAppbarColor(
            requireContext(),
            this,
            appBarLayout
        )
        recyclerView.addOnScrollListenerWithImageVisibility(imageLogoDefault, imageLogoScrolled)
    }

}


