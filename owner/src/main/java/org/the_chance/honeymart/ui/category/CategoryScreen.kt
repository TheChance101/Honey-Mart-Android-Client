package org.the_chance.honeymart.ui.category

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.ui.category.categories.CategoriesViewModel
import org.the_chance.honeymart.ui.products.ProductsViewModel

@Composable
fun CategoryScreen(
    categoriesViewModel: CategoriesViewModel = hiltViewModel(),
    productsViewModel: ProductsViewModel = hiltViewModel()
) {
    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Category",
        textAlign = TextAlign.Center
    )
}
