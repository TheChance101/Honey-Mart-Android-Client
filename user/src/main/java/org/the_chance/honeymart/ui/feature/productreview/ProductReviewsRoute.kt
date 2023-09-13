package org.the_chance.honeymart.ui.feature.productreview

import androidx.navigation.NavController
import org.the_chance.honeymart.ui.navigation.Screen

private val ROUTE = Screen.ProductReviewsScreen.route

fun NavController.navigateToProductReviewsScreen(productId: Long) {
    navigate("${ROUTE}/$productId")
}

