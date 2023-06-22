package org.the_chance.honeymart.ui.feature.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.user.R

@AndroidEntryPoint
class CartBottomFragment : BottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.bottom_sheet_complete_cart_order, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.button_discover_markets)
        button.setOnClickListener(this)
    }
    private fun navigateToOrderComplete() {
        findNavController().navigate(R.id.action_cartBottomFragment_to_ordersFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_discover_markets -> {
                navigateToOrderComplete()
            }
        }
    }

}