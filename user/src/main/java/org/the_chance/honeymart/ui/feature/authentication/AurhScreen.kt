package org.the_chance.honeymart.ui.feature.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.fragment.findNavController
import org.the_chance.design_system.R
import org.the_chance.honeymart.util.AuthData
import org.the_chance.honymart.ui.composables.CustomButton
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.primary100

@Composable
fun AuthScreen(
    view: AuthFragment,
    authData: AuthData,
    viewModel: AuthViewModel = hiltViewModel()
) {
    AuthContent(
        onClickSignUp = {
            view.findNavController()
                .navigate(AuthFragmentDirections.actionAuthFragmentToSignupFragment(authData))
        },
        onClickLogin = {
            view.findNavController()
                .navigate(AuthFragmentDirections.actionAuthFragmentToLoginFragment(authData))
        },
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
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(R.drawable.background_frame_auth),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(R.string.welcome_to_honey_mart),
            modifier = Modifier.padding(top = 16.dp),
            color = black60,
            style = Typography.displayMedium
        )
        Text(
            text = stringResource(R.string.get_ready_to_a_shopping_experience_like_no_other),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            color = black37,
            style = Typography.bodySmall,
            textAlign = TextAlign.Center
        )
        CustomButton(
            labelIdStringRes = R.string.sign_up,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 40.dp),
            onClick = onClickSignUp
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
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