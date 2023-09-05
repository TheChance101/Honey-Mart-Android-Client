package org.the_chance.honeymart.ui.feature.authentication.signup

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.composables.NavigationHandler
import org.the_chance.honeymart.ui.feature.authentication.login.navigateToLogin
import org.the_chance.honeymart.ui.feature.authentication.signup.authentication.AuthScreen
import org.the_chance.honeymart.ui.feature.authentication.signup.composables.AuthScaffold
import org.the_chance.honeymart.ui.feature.authentication.signup.composables.FirstSignupFiledContent
import org.the_chance.honeymart.ui.feature.authentication.signup.composables.SecondSignupFieldContent
import org.the_chance.honeymart.ui.feature.authentication.signup.composables.SignupFooter
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun SignupScreen(viewModel: SignupViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                SignupUiEffect.ClickLoginEffect -> navController.navigateToLogin()

                SignupUiEffect.ClickSignupEffect -> {

                    navController.popBackStack(
                        Screen.SignupScreen.route, true
                    )
                }

                SignupUiEffect.ShowToastEffect ->
                    Toast.makeText(
                        context,
                        state.validationToast.message,
                        Toast.LENGTH_LONG
                    ).show()
            }
        })

    SignupContent(listener = viewModel, state = state)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupContent(
    modifier: Modifier = Modifier,
    listener: SignupInteractionListener,
    state: SignupUiState,
) {
    ContentVisibility(state = state.isAuthScreenVisible) {
        AuthScreen()
    }
    ContentVisibility(state = !state.isAuthScreenVisible) {
        Box(
            modifier = Modifier.imePadding().fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
                modifier = modifier
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
            ) {
                AuthScaffold(
                    title = stringResource(id = R.string.sign_up),
                    description = stringResource(
                        id = R.string.create_your_account_and_enter_a_world_of_endless_shopping_possibilities
                    ),
                )
                val pagerState = rememberPagerState()
                Box(modifier = Modifier.imePadding()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            pageCount = 2,
                            userScrollEnabled = false
                        ) { page ->
                            when (page) {
                                0 -> FirstSignupFiledContent(
                                    state = state,
                                    listener = listener,
                                    pagerState = pagerState
                                )

                                1 -> SecondSignupFieldContent(state = state, listener = listener,pagerState)
                            }
                        }
                    }
                }
                if (pagerState.currentPage == 0) {
                    SignupFooter(listener = listener)
                }
            }
        }
    }

}