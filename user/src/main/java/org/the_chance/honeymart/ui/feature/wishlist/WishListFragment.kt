package org.the_chance.honeymart.ui.feature.wishlist

import androidx.fragment.app.viewModels
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentWishListBinding

class WishListFragment : BaseFragment<FragmentWishListBinding>() {
    override val TAG: String = this::class.java.simpleName
    override val layoutIdFragment: Int = R.layout.fragment_wish_list
    override val viewModel: WishListViewModel by viewModels()

}