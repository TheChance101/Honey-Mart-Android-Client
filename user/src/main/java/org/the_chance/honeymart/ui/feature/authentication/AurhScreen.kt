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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.the_chance.design_system.R
import org.the_chance.honeymart.ui.LocalNavigationProvider
import org.the_chance.honeymart.ui.feature.login.navigateToLogin
import org.the_chance.honeymart.ui.feature.signup.navigateToSignupScreen
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun AuthScreen() {
    val navController = LocalNavigationProvider.current

    AuthContent(
        onClickSignUp = { navController.navigateToSignupScreen() },
        onClickLogin = { navController.navigateToLogin() },
    )
}

@Composable
fun AuthContent(
    onClickSignUp: () -> Unit,
    onClickLogin: () -> Unit,
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
            color = black37,
            style = Typography.displayMedium
        )
        Text(
            text = stringResource(R.string.get_ready_to_a_shopping_experience_like_no_other),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space16
            ),
            color = black37,
            style = Typography.bodySmall,
            textAlign = TextAlign.Center
        )
        CustomButton(
            label = stringResource(id =R.string.sign_up ),
            modifier = Modifier.padding(
                horizontal = MaterialTheme.dimens.space16,
                vertical = MaterialTheme.dimens.space40
            ),
            onClick = onClickSignUp
        )
        Row(
            modifier = Modifier.padding(top = MaterialTheme.dimens.space16),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.already_have_account),
                color = black37,
                style = Typography.displaySmall,
                textAlign = TextAlign.Center
            )
            TextButton(
                contentPadding = PaddingValues(MaterialTheme.dimens.zero),
                onClick = onClickLogin, colors = ButtonDefaults.textButtonColors(Color.Transparent)
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