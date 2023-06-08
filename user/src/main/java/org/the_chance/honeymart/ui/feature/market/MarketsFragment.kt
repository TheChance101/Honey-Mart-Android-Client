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
  /*      *//*imageLogoDefault = requireView().findViewById(R.id.image_logo)
        imageLogoScrolled = requireView().findViewById(R.id.image_logo_scro*//*ll)*/

        appBarLayout = requireActivity().findViewById(R.id.appBarLayout)


/*        binding.recyclerMarkets.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val offset = recyclerView.computeVerticalScrollOffset()
                    val threshold = resources.getDimensionPixelSize(org.the_chance.design_system.R.dimen.spacing_medium)
                    val alpha = (offset.toFloat() / threshold).coerceIn(0f, 1f)
                    val newColor = interpolateColor( ContextCompat.getColor(requireContext(), org.the_chance.design_system.R.color.white_300),
                        ContextCompat.getColor(requireContext(), org.the_chance.design_system.R.color.primary_100),
                        alpha)
                    appBarLayout.setBackgroundColor(newColor)
                }
            })*/

        binding.recyclerMarkets.addOnScrollListenerWithAppbarColor(requireContext(), appBarLayout)


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

    private fun interpolateColor(color1: Int, color2: Int, ratio: Float): Int {
        val r = (Color.red(color1) * (1 - ratio) + Color.red(color2) * ratio).toInt()
        val g = (Color.green(color1) * (1 - ratio) + Color.green(color2) * ratio).toInt()
        val b = (Color.blue(color1) * (1 - ratio) + Color.blue(color2) * ratio).toInt()
        return Color.rgb(r, g, b)
    }

}