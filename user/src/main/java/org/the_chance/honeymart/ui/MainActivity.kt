package org.the_chance.honeymart.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.user.R
import org.the_chance.user.databinding.ActivityMainBinding


@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("AppCompatMethod")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navHostFragment.navController
        val navController = navHostFragment.navController

        setupNavigation(navController)
        statusBarMode(this)

    }

    private fun statusBarMode(activity: Activity) {
        activity.window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            when (context.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                }

                else -> decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            statusBarColor = Color.TRANSPARENT
        }
    }


    private fun setupNavigation(navController: NavController) {
        val navView = binding.bottomNavigationView
        val destinations = setOf(
            R.id.marketsFragment,
            R.id.cartFragment,
            R.id.ordersFragment,
            R.id.wishListFragment
        )

        navView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                /*R.id.marketsFragment -> {
                    navController.popBackStack(R.id.marketsFragment, true)
                }
                R.id.cartFragment -> {
                    navController.popBackStack(R.id.cartFragment, true)
                }
                R.id.ordersFragment -> {
                    navController.popBackStack(R.id.ordersFragment, true)
                }
                R.id.wishListFragment -> {
                    navController.popBackStack(R.id.wishListFragment, true)
                }*/
                in destinations ->{
                    navController.popBackStack(navController.graph.startDestinationId,false)
                }
                /*R.id.markets_graph -> {
                        navController.popBackStack(navController.graph.startDestinationId, false)
                }

                R.id.cart_graph -> {
                    if (navController.currentDestination?.id == R.id.cart_graph) {
                        navController.popBackStack(R.id.cart_graph, false)
                    } else {
                        navController.popBackStack(navController.graph.startDestinationId, false)
                    }
                }

                R.id.orders_graph -> {
                    if (navController.currentDestination?.id == R.id.orders_graph) {
                        navController.popBackStack(R.id.orders_graph, false)
                    } else {
                        navController.popBackStack(navController.graph.startDestinationId, false)
                    }
                }

                R.id.wishListFragment -> {
                    if (navController.currentDestination?.id == R.id.wishListFragment) {
                        navController.popBackStack(R.id.wishListFragment, false)
                    } else {
                        navController.popBackStack(navController.graph.startDestinationId, false)
                    }
                }*/
            }
        }

        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

