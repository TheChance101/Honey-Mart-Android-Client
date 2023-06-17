package org.the_chance.honeymart.ui.base

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


abstract class BaseFragment<VB : ViewDataBinding> : Fragment() {

    abstract val TAG: String
    abstract val layoutIdFragment: Int
    abstract val viewModel: ViewModel
    private lateinit var _binding: VB
    protected val binding get() = _binding

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
            setVariable(BR.viewModel,viewModel)
            return root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val window: Window = requireActivity().window
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }

    protected open fun setup() {}

    protected fun log(value: Any) {
        Log.e(TAG, value.toString())
    }
}


