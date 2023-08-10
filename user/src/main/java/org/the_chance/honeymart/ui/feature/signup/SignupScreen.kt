package org.the_chance.honeymart.ui.feature.signup

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.composables.ContentVisibility
import org.the_chance.honeymart.ui.feature.login.compsoables.CustomDialog
import org.the_chance.honeymart.ui.feature.login.navigateToLogin
import org.the_chance.honeymart.ui.navigation.Screen
import org.the_chance.honeymart.util.collect
import org.the_chance.honymart.ui.composables.HoneyAuthFooter
import org.the_chance.honymart.ui.composables.HoneyAuthHeader
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.white

@Composable
fun SignupScreen(viewModel: SignupViewModel = hiltViewModel()) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    lifecycleOwner.collect(viewModel.effect) { effect ->
        effect.getContentIfHandled()?.let {
            when (it) {
                SignupUiEffect.ClickLoginEffect -> navController.navigateToLogin()
                SignupUiEffect.ClickSignupEffect -> navController.popBackStack(
                    Screen.AuthenticationScreen.route,
                    true
                )

                SignupUiEffect.ShowToastEffect ->
                    Toast.makeText(context, "User name or email already exist", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    SignupContent(listener = viewModel, state = state)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupContent(
    modifier: Modifier = Modifier,
    listener: SignupInteractionListener,
    state: SignupUiState,
) {
    Loading(state = state.isLoading)
    ContentVisibility(state = !state.isLoading) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16),
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.background_frame),
                    contentDescription = "",
                    modifier = modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space24),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = MaterialTheme.dimens.space16)
                ) {
                    HoneyAuthHeader(
                        title = stringResource(R.string.sign_up),
                        subTitle = stringResource(
                            R.string
                                .create_your_account_and_enter_a_world_of_endless_shopping_possibilities
                        ),
                        subTitleColor = white,
                        modifier = Modifier
                            .padding(bottom = MaterialTheme.dimens.space24)
                            .align(
                                Alignment.CenterHorizontally
                            )
                    )
                }
            }
            val pagerState = rememberPagerState()

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
                        0 -> Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                        ) {
                            HoneyTextField(
                                text = state.fullName,
                                hint = stringResource(R.string.full_name),
                                iconPainter = painterResource(id = R.drawable.ic_person),
                                onValueChange = listener::onFullNameInputChange,
                                errorMessage = when (state.fullNameState) {
                                    ValidationState.BLANK_FULL_NAME -> "name cannot be blank"
                                    ValidationState.INVALID_FULL_NAME -> "Invalid name"
                                    else -> ""
                                },
                            )
                            HoneyTextField(
                                text = state.email,
                                hint = stringResource(R.string.email),
                                iconPainter = painterResource(id = R.drawable.ic_email),
                                onValueChange = listener::onEmailInputChange,
                                errorMessage = when (state.emailState) {
                                    ValidationState.BLANK_EMAIL -> "email cannot be blank"
                                    ValidationState.INVALID_EMAIL -> "Invalid email"
                                    else -> ""
                                },
                            )
                        }

                        1 -> Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.space16)
                        ) {
                            HoneyTextField(
                                text = state.password,
                                hint = stringResource(R.string.password),
                                iconPainter = painterResource(id = R.drawable.ic_password),
                                onValueChange = listener::onPasswordInputChanged,
                                errorMessage = when (state.passwordState) {
                                    ValidationState.BLANK_PASSWORD -> "Password cannot be blank"
                                    ValidationState.INVALID_PASSWORD -> "Invalid password"
                                    ValidationState.INVALID_PASSWORD_LENGTH -> "Password must be at least 8 characters"
                                    else -> ""
                                },
                            )
                            HoneyTextField(
                                text = state.confirmPassword,
                                hint = stringResource(R.string.confirm_password),
                                iconPainter = painterResource(id = R.drawable.ic_password),
                                onValueChange = listener::onConfirmPasswordChanged,
                                errorMessage = when (state.confirmPasswordState) {
                                    ValidationState.INVALID_CONFIRM_PASSWORD -> "Invalid confirm password"
                                    else -> ""
                                }
                            )
                        }
                    }
                }
                val coroutineScope = rememberCoroutineScope()
                when (pagerState.currentPage) {
                    0 -> HoneyFilledButton(
                        label = stringResource(id = R.string.Continue),
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.dimens.space16,
                            vertical = MaterialTheme.dimens.space40
                        ),
                        onClick = {
                            if (state.continueValidation()) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                            }
                        },
                    )

                    1 -> HoneyFilledButton(
                        label = stringResource(id = R.string.sign_up),
                        modifier = Modifier.padding(
                            horizontal = MaterialTheme.dimens.space16,
                            vertical = MaterialTheme.dimens.space40
                        ),
                        onClick = listener::onClickSignup,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            when (pagerState.currentPage) {
                0 ->
                    HoneyAuthFooter(
                        text = stringResource(R.string.already_have_account),
                        textButtonText = stringResource(R.string.log_in),
                        onTextButtonClicked = listener::onClickLogin,
                        modifier = Modifier.Companion.align(Alignment.CenterHorizontally)
                    )
            }
        }
    }
    if (state.isLogin == ValidationState.SUCCESS) {
        CustomDialog(openDialogCustom = remember { mutableStateOf(true) })
    }
}