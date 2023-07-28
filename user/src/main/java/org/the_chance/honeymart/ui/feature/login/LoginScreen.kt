package org.the_chance.honeymart.ui.feature.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.fragment.findNavController
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.feature.product.ProductsFragmentDirections
import org.the_chance.honeymart.ui.feature.product_details.ProductDetailsFragmentDirections
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.composables.TextField
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun LoginScreen(
    view: LoginFragment,
    authData: AuthData,
    args: LoginFragmentArgs,
    viewModel: LoginViewModel = hiltViewModel(),

    ) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(key1 = state.isLogin){
        if (state.isLogin){
            val action = when (authData) {
                is AuthData.Products -> {
                    ProductsFragmentDirections.actionGlobalProductsFragment(
                        authData.categoryId,
                        authData.marketId,
                        authData.position
                    )
                }
                is AuthData.ProductDetails -> {
                    ProductDetailsFragmentDirections.actionGlobalProductDetailsFragment(
                        authData.productId
                    )
                }
            }
            view.findNavController().setGraph(org.the_chance.user.R.navigation.main_nav_graph)
            view.findNavController().navigate(action)
        }
    }

    LaunchedEffect(key1 = state.emailState, key2 = state.passwordState){
        if (state.emailState == ValidationState.INVALID_EMAIL
            || state.passwordState == ValidationState.INVALID_PASSWORD){
            Toast.makeText(context,"Email or password not exist", Toast.LENGTH_SHORT).show()
        }
    }

    viewModel.saveArgs(args)
    LoginContent(
        onClickLogin = viewModel::onLoginClick,

        onClickSignup = {
            view.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment(authData))
        },
        onEmailChange = viewModel::onEmailInputChange,
        onPasswordChange = viewModel::onPasswordInputChanged,
        state = state
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
    onClickSignup: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    state: LoginUiState,
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
                        text = stringResource(R.string.welcome_back),
                        color = white,
                        style = Typography.headlineMedium,
                    )
                    Text(
                        text = stringResource(
                            R.string.reconnect_with_your_favorite_brands_and_saved_items_log_in_today
                        ),
                        color = white,
                        style = Typography.bodyMedium,
                    )
                }
            }
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
            CustomButton(
                labelIdStringRes = R.string.log_in,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
                onClick = onClickLogin,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    color = black37,
                    style = Typography.displaySmall,
                    textAlign = TextAlign.Center
                )
                TextButton(
                    onClick = onClickSignup,
                    colors = ButtonDefaults.textButtonColors(Color.Transparent)
                ) {
                    Text(
                        text = stringResource(R.string.Sign_up),
                        color = primary100,
                        style = Typography.displayLarge,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}