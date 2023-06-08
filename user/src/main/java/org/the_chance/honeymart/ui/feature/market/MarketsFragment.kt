package org.the_chance.honeymart.ui.feature.market

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.base.BaseFragment
import org.the_chance.honeymart.ui.util.addOnScrollListenerWithAppbarColor
import org.the_chance.honeymart.ui.util.addOnScrollListenerWithImageVisibility
import org.the_chance.user.R
import org.the_chance.user.databinding.FragmentMarketsBinding

@AndroidEntryPoint
class MarketsFragment : BaseFragment<FragmentMarketsBinding>() {
    override val TAG: String = "TAG"
    override val layoutIdFragment = R.layout.fragment_markets
    override val viewModel: MarketViewModel by viewModels()
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var imageLogoDefault: ShapeableImageView
    private lateinit var imageLogoScrolled: ShapeableImageView
    override fun setup() {
        val adapter = MarketAdapter(viewModel)
        binding.recyclerMarkets.adapter = adapter
        appBarLayout = requireActivity().findViewById(R.id.appBarLayout)
        imageLogoDefault = requireActivity().findViewById(R.id.image_logo)
        imageLogoScrolled = requireActivity().findViewById(R.id.image_logo_scroll)
        binding.recyclerMarkets.addOnScrollListenerWithAppbarColor(requireContext(), appBarLayout)
        binding.recyclerMarkets.addOnScrollListenerWithImageVisibility(imageLogoDefault, imageLogoScrolled)

        /*        binding.recyclerMarkets.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                            // Scrolling has ended or is settling
                            imageLogoDefault.visibility = View.VISIBLE
                            imageLogoScrolled.visibility = View.INVISIBLE
                        } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                            // Scrolling is in progress
                            imageLogoDefault.visibility = View.INVISIBLE
                            imageLogoScrolled.visibility = View.VISIBLE
                        }
                    }
                })*/

    }
}