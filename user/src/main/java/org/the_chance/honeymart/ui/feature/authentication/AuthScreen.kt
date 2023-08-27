package org.the_chance.honeymart.ui.feature.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.NavigationHandler
import org.the_chance.honeymart.ui.feature.login.navigateToLogin
import org.the_chance.honeymart.ui.feature.signup.navigateToSignupScreen
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
) {
    NavigationHandler(
        effects = viewModel.effect,
        handleEffect = { effect, navController ->
            when (effect) {
                is AuthenticationUiEffect.ClickLoginEffect -> navController.navigateToLogin()
                is AuthenticationUiEffect.ClickSignUpEffect -> navController.navigateToSignupScreen()
            }
    })

    AuthContent(listener = viewModel)
}

@Composable
fun AuthContent(
    listener: AuthenticationInteractionListener,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(
                enabled = true,
                state = rememberScrollState()
            )
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
        Row(
            modifier = Modifier.padding(top = MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.already_have_account),
                style = Typography.displaySmall.copy(black37),
                textAlign = TextAlign.Center
            )
            TextButton(
                contentPadding = PaddingValues(MaterialTheme.dimens.zero),
                onClick = listener::onClickLogin,
                colors = ButtonDefaults.textButtonColors(Color.Transparent)
            ) {
                Text(
                    text = stringResource(R.string.log_in),
                    style = Typography.displayLarge.copy(primary100),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}


@Preview(device = "id:3.2in HVGA slider (ADP1)")
@Composable
fun AuthScreenPreview() {
    AuthContent(listener = object : AuthenticationInteractionListener {
        override fun onClickLogin() {
        }

        override fun onClickSignUp() {
        }

    })
}
@Preview
@Composable
fun AuthScreenPreview2() {
    AuthContent(listener = object : AuthenticationInteractionListener {
        override fun onClickLogin() {
        }

        override fun onClickSignUp() {
        }

    })
}