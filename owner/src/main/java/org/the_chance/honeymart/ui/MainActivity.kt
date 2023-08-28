package org.the_chance.honeymart.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.ui.main.MainScreen
import org.the_chance.honeymart.ui.navigation.LocalNavigationProvider
import org.the_chance.honeymart.ui.navigation.RootNavigationGraph
import org.the_chance.honymart.ui.theme.HoneyMartTheme


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                HoneyMartTheme {
                    MainScreen()
                }
            }
        }
    }
}

