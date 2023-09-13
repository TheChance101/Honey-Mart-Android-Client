package org.the_chance.honeymart.ui.feature.marketInfo.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesAppBarScaffold(
    navController: NavController,
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isScrolled = remember { derivedStateOf { scrollBehavior.state.contentOffset < -100f } }

    val systemUiController = rememberSystemUiController()

    val currentNightMode =
        LocalContext.current.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    val isDarkMode = currentNightMode == Configuration.UI_MODE_NIGHT_YES

    val darkIcons = if (!isDarkMode) !isScrolled.value else false

    systemUiController.setSystemBarsColor(
        color = Color.Unspecified,
        darkIcons = darkIcons,
        isNavigationBarContrastEnforced = false
    )
    val topAppBarContainerColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.inverseOnSurface
    } else {
        MaterialTheme.colorScheme.background
    }
    val topAppBarTitleColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSecondary
    }
    val iconBackColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSecondary
    }
    Scaffold(
        topBar = {
            TopAppBar(
                {
                    CategoriesScreenTopBar(
                        onClickBack = { navController.navigateUp() },
                        titleColor = topAppBarTitleColor,
                        iconColor = iconBackColor
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = topAppBarContainerColor,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            content()
        }
    }
}
