package org.the_chance.honeymart.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.util.setupScrollingAppBar
import org.the_chance.user.R
import org.the_chance.user.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setupScrollingAppBar(
            binding.appBarLayout,
            binding.imageLogo,
            ContextCompat.getColor(this, org.the_chance.design_system.R.color.white_300),
            ContextCompat.getColor(this, org.the_chance.design_system.R.color.primary_100),
            org.the_chance.design_system.R.drawable.logo_honey_mart,
            org.the_chance.design_system.R.drawable.logo_honey_mart_when_scroll
        )


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navHostFragment.navController
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.authFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.signupFragment
            ) {
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        val navGraph=navController.navInflater.inflate(R.navigation.main_nav_graph)

        setupNavigation(navController)
    }


    private fun setupNavigation(navController: NavController) {
        val navView = binding.bottomNavigationView
        setOf(
            R.id.marketsFragment,
            R.id.cartFragment,
            R.id.ordersFragment,
            R.id.wishListFragment
        )

        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

