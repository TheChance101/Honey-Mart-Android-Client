package org.the_chance.honymart.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarScaffold(
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isScrolled =
        remember { derivedStateOf { scrollBehavior.state.contentOffset < -100f } }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent,
        darkIcons = !isScrolled.value,
        isNavigationBarContrastEnforced = false
    )
    val topAppBarContainerColor = if (isScrolled.value) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface
    }
    val topAppBarTitleColor = if (isScrolled.value) {
        white
    } else {
        primary100
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppBarTitle(topAppBarTitleColor) },
                navigationIcon = { },
                actions = { },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = topAppBarContainerColor,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            content()
        }
    }
}

@Composable
fun AppBarTitle(
    titleColor: Color,
) {
    Row {
        Icon(
            modifier = Modifier
                .size(32.dp)
                .padding(end = MaterialTheme.dimens.space4),
            painter = painterResource(id = R.drawable.icon_cart),
            contentDescription = stringResource(R.string.title_icon),
            tint = titleColor
        )
        Text(
            style = MaterialTheme.typography.displayMedium.copy(color = titleColor),
            text = stringResource(R.string.honey)
        )
        Text(
            text = stringResource(R.string.mart),
            style = MaterialTheme.typography.displayMedium,
        )
    }
}