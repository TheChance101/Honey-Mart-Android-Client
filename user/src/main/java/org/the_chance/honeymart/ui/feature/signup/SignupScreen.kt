package org.the_chance.honeymart.ui.feature.signup

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.login.navigateToLogin
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.composables.TextField
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun SignupScreen(viewModel: SignupViewModel = hiltViewModel()) {
    val navController = LocalNavigationProvider.current
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = state.emailState, key2 = state.fullNameState){
        if (state.emailState == ValidationState.INVALID_EMAIL
            || state.fullNameState == ValidationState.INVALID_FULL_NAME){
            Toast.makeText(context,"User name or email already exist",Toast.LENGTH_SHORT).show()
        }
    }

//    LaunchedEffect(key1 = state.isLogin ){
//        if (state.isLogin == ValidationState.SUCCESS){
//            val action = when (authData) {
//                is AuthData.Products -> {
//                    ProductsFragmentDirections.actionGlobalProductsFragment(
//                        authData.categoryId,
//                        authData.marketId,
//                        authData.position
//                    )
//                }
//
//                is AuthData.ProductDetails -> {
//                    ProductDetailsFragmentDirections.actionGlobalProductDetailsFragment(
//                        authData.productId
//                    )
//                }
//            }
//            view.findNavController().setGraph(org.the_chance.user.R.navigation.main_nav_graph)
//            view.findNavController().navigate(action)
//        }
//    }
//    viewModel.saveArgs(args)
    SignupContent(
        onNameChange = viewModel::onFullNameInputChange,
        onEmailChange = viewModel::onEmailInputChange,
        onPasswordChange = viewModel::onPasswordInputChanged,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChanged,
        onClickLogin = { navController.navigateToLogin() },
        onClickSignup = viewModel::onClickSignup,
        state = state
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignupContent(
    modifier: Modifier = Modifier,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onClickLogin: () -> Unit,
    onClickSignup: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    state: SignupUiState
) {
    if (state.isLoading){
        // loading animation
    }else{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(R.drawable.background_frame),
                    contentDescription = "",
                    modifier = modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.sign_up),
                        color = white,
                        style = Typography.headlineMedium,
                    )
                    Text(
                        text = stringResource(
                            R.string
                                .create_your_account_and_enter_a_world_of_endless_shopping_possibilities
                        ),
                        color = white,
                        style = Typography.bodyMedium,
                    )
                }
            }
            val pagerState = rememberPagerState()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HorizontalPager(state = pagerState, pageCount = 2, userScrollEnabled = false) { page ->
                    when(page){
                        0 -> Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            TextField(
                                text = state.fullName,
                                hint = stringResource(R.string.full_name),
                                idIconDrawableRes = R.drawable.ic_person,
                                onValueChange = onNameChange,
                                errorMessage = when (state.fullNameState) {
                                    ValidationState.BLANK_FULL_NAME -> "name cannot be blank"
                                    ValidationState.INVALID_FULL_NAME -> "Invalid name"
                                    else -> ""
                                },
                            )
                            TextField(
                                text = state.email,
                                hint = stringResource(R.string.email),
                                idIconDrawableRes = R.drawable.ic_email,
                                onValueChange = onEmailChange,
                                errorMessage = when (state.emailState) {
                                    ValidationState.BLANK_EMAIL -> "email cannot be blank"
                                    ValidationState.INVALID_EMAIL -> "Invalid email"
                                    else -> ""
                                },
                            )
                        }
                        1 -> Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            TextField(
                                text = state.password,
                                hint = stringResource(R.string.password),
                                idIconDrawableRes = R.drawable.ic_password,
                                onValueChange = onPasswordChange,
                                errorMessage = when (state.passwordState) {
                                    ValidationState.BLANK_PASSWORD -> "Password cannot be blank"
                                    ValidationState.INVALID_PASSWORD -> "Invalid password"
                                    ValidationState.INVALID_PASSWORD_LENGTH -> "Password must be at least 8 characters"
                                    else -> ""
                                },
                            )
                            TextField(
                                text = state.confirmPassword,
                                hint = stringResource(R.string.confirm_password),
                                idIconDrawableRes = R.drawable.ic_password,
                                onValueChange = onConfirmPasswordChange,
                                errorMessage = when(state.confirmPasswordState) {
                                    ValidationState.INVALID_CONFIRM_PASSWORD -> "Invalid confirm password"
                                    else -> ""
                                }
                            )
                        }
                    }
                }
                val coroutineScope = rememberCoroutineScope()
                when(pagerState.currentPage){
                    0 -> CustomButton(
                        labelIdStringRes = R.string.Continue,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
                        onClick = {
                            if (state.fullNameState == ValidationState.VALID_FULL_NAME
                                && state.emailState == ValidationState.VALID_EMAIL &&
                                state.email.isNotEmpty() && state.fullName.isNotEmpty()) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(1)
                                }
                            }
                        },
                    )
                    1 -> CustomButton(
                        labelIdStringRes = R.string.sign_up,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
                        onClick = onClickSignup
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            when(pagerState.currentPage){
                0 -> Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 32.dp)
                ) {
                    Text(
                        text = stringResource(R.string.already_have_account),
                        color = black37,
                        style = Typography.displaySmall,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = onClickLogin , colors = ButtonDefaults.textButtonColors(Color.Transparent)) {
                        Text(
                            text = stringResource(R.string.log_in),
                            color = primary100,
                            style = Typography.displayLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}