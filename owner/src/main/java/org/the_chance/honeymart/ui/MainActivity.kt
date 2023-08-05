package org.the_chance.honeymart.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.add_product.AddProductScreen
import org.the_chance.honymart.ui.theme.HoneyMartTheme
import org.the_chance.owner.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HoneyMartTheme {
                AddProductScreen()
            }
        }
    }
}