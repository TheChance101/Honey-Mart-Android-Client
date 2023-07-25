package org.the_chance.honeymart.ui.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.design_system.R
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.composables.TextField
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.honymart.ui.theme.white

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    LoginContent(
        onClickLogin = viewModel::onLoginClick,
        onClickSignup = viewModel::onClickSignUp,
        onEmailChange = viewModel::onEmailInputChange,
        onPasswordChange = viewModel::onPasswordInputChanged
    )
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onClickLogin: () -> Unit,
    onClickSignup: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
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
            hint = "Email",
            idIconDrawableRes = R.drawable.ic_email,
            onValueChange = onEmailChange
            )
        TextField(
            hint = "Password",
            idIconDrawableRes = R.drawable.ic_password,
            onValueChange = onPasswordChange
        )
        CustomButton(
            labelIdStringRes = R.string.log_in,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
            onClick = onClickLogin
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
            Text(
                text = stringResource(R.string.Sign_up),
                color = primary100,
                style = Typography.displayLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.clickable { onClickSignup }
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreen()
}