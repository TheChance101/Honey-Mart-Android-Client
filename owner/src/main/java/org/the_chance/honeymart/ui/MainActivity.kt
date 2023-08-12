package org.the_chance.honeymart.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.the_chance.honeymart.LocalNavigationProvider
import org.the_chance.honeymart.ui.features.login.LoginScreen
import org.the_chance.honeymart.ui.features.signup.SignupScreen
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoScreen
import org.the_chance.honeymart.ui.navigation.MainNavGraph
import org.the_chance.honeymart.ui.navigation.NavigationRail
import org.the_chance.honymart.ui.theme.HoneyMartTheme

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(LocalNavigationProvider provides rememberNavController()) {
                HoneyMartTheme {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .fillMaxSize()
                    )
                    {
                        NavigationRail()
                        MainNavGraph()
                    }
                }
            }
        }
    }
}

@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun Preview() {
    NavigationRail()
}
