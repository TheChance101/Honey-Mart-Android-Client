package org.the_chance.honeymart.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import org.the_chance.honeymart.ui.main.MainInteractionListener
import org.the_chance.honeymart.ui.main.MainUiState
import org.the_chance.honymart.ui.composables.ImageNetwork
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white
import java.util.Locale

@Composable
fun NavigationRail(
    state: MainUiState,
    listener: MainInteractionListener,
) {
    val navController = LocalNavigationProvider.current
    val screens = listOf(
        NavigationRailScreen.Orders,
        NavigationRailScreen.Category,
        NavigationRailScreen.Coupons,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationRail(
        containerColor = MaterialTheme.colorScheme.onTertiary,
        header = {
            if (state.ownerImageUrl.isNotEmpty()) {
                ImageNetwork(
                    modifier = Modifier
                        .size(MaterialTheme.dimens.icon48)
                        .clip(CircleShape)
                        .clickable { listener.onClickProfile() },
                    imageUrl = state.ownerImageUrl,
                )
            } else {
                Box(
                    modifier = Modifier.
                        padding(top = MaterialTheme.dimens.space8)
                        .size(MaterialTheme.dimens.icon48)
                        .clip(CircleShape)
                        .background(
                            MaterialTheme.colorScheme.primary,
                        )
                        .clickable { listener.onClickProfile() },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = state.ownerNameFirstCharacter,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = white,
                            baselineShift = BaselineShift(0.2f)
                        )
                    )
                }
            }
        }
    ) {
        Spacer(modifier = Modifier.weight(1f))
        screens.forEach { screen ->
            AppNavRailItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .clickable { listener.onClickLogout() }
                .padding(16.dp),
            painter = painterResource(id = org.the_chance.design_system.R.drawable.ic_logout),
            contentDescription = "Logout Icon",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}