package org.the_chance.honeymart.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.features.add_product.AddProductScreen
import org.the_chance.honeymart.ui.features.update_category.UpdateCategoryScreen
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HoneyMartTheme {
                UpdateCategoryScreen()
            }
        }
    }
}