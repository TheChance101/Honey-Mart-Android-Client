package org.the_chance.honeymart.ui.feature.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.login.navigateToLogin
import org.the_chance.honeymart.ui.feature.signup.navigateToSignupScreen
import org.the_chance.honymart.ui.composables.HoneyAuthFooter
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val navController = LocalNavigationProvider.current

    LaunchedEffect(key1 = true) {
        viewModel.effect.collect {
            when (it) {
                is AuthenticationUiEffect.ClickLoginEffect -> navController.navigateToLogin()
                is AuthenticationUiEffect.ClickSignUpEffect -> navController.navigateToSignupScreen()

            }
        }
    }

    AuthContent(listener = viewModel)
}

@Composable
fun AuthContent(
    listener: AuthenticationInteractionListener,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)

    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.background_frame_auth),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.welcome_to_honey_mart),
            modifier = Modifier.padding(top = MaterialTheme.dimens.space16),
            style = Typography.displayMedium.copy(black37)
        )
        Text(
            text = stringResource(R.string.get_ready_to_a_shopping_experience_like_no_other),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space16
            ),
            style = Typography.bodySmall.copy(black37),
            textAlign = TextAlign.Center
        )
        HoneyFilledButton(
            label = stringResource(id = R.string.sign_up),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space40
            ),
            onClick = listener::onClickSignUp
        )
        HoneyAuthFooter(
            text = stringResource(R.string.already_have_account),
            textButtonText = stringResource(R.string.log_in),
            onTextButtonClicked = listener::onClickLogin,
            modifier = Modifier.Companion.align(Alignment.CenterHorizontally)
        )
    }
}