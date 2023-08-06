package org.the_chance.honeymart.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import org.the_chance.honymart.ui.composables.Loading
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.owner.R


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val state by viewModel.state.collectAsState()

    LoginContent(listener = viewModel, state = state)
}

@Composable
fun LoginContent(
    listener: LoginInteractionListener,
    state: LoginUiState,
) {
    Loading(state.isLoading)

    Box {
        Row {

            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxHeight()
            )


            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(
                        end = MaterialTheme.dimens.space56,
                        bottom = MaterialTheme.dimens.space16,
                        top = MaterialTheme.dimens.space56
                    ),
                verticalArrangement = Arrangement.Center,
            ) {

                Text(
                    text = stringResource(org.the_chance.design_system.R.string.welcome_back),
                    style = Typography.headlineMedium.copy(color = black60),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.dimens.space24)
                        .align(
                            Alignment.CenterHorizontally
                        )
                )

                Text(
                    text = stringResource(
                        org.the_chance.design_system.R.string.login_to_discover_a_curated_selection_of_products_just_for_you
                    ),
                    style = Typography.bodyMedium.copy(color = black60),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 100.dp)
                )

                HoneyTextField(
                    text = state.email,
                    hint = stringResource(org.the_chance.design_system.R.string.email),
                    iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.ic_email),
                    onValueChange = listener::onEmailInputChange,
                    errorMessage = when (state.emailState) {
                        ValidationState.BLANK_EMAIL -> "email cannot be blank"
                        ValidationState.INVALID_EMAIL -> "Invalid email"
                        else -> ""
                    },
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.space16))
                HoneyTextField(
                    text = state.password,
                    hint = stringResource(org.the_chance.design_system.R.string.password),
                    iconPainter = painterResource(id = org.the_chance.design_system.R.drawable.ic_password),
                    onValueChange = listener::onPasswordInputChanged,
                    errorMessage = when (state.passwordState) {
                        ValidationState.BLANK_PASSWORD -> "Password cannot be blank"
                        ValidationState.INVALID_PASSWORD -> "Invalid password"
                        ValidationState.INVALID_PASSWORD_LENGTH -> "Password must be at least 8 characters"
                        else -> ""
                    },
                )
                HoneyFilledButton(
                    label = stringResource(id = org.the_chance.design_system.R.string.log_in),
                    modifier = Modifier.padding(
                        horizontal = MaterialTheme.dimens.space16,
                        vertical = MaterialTheme.dimens.space40
                    ),
                    onClick = listener::onClickLogin,
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(org.the_chance.design_system.R.string.don_t_have_an_account),
                        style = Typography.displaySmall.copy(color = black37),
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        onClick = listener::onClickSignup,
                        colors = ButtonDefaults.textButtonColors(Color.Transparent)
                    ) {
                        Text(
                            text = stringResource(org.the_chance.design_system.R.string.Sign_up),
                            style = Typography.displayLarge.copy(color = primary100),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }


        Image(
            painter = painterResource(org.the_chance.design_system.R.drawable.image_group),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(TopEnd)
                .size(120.dp)
        )


        Row(
            modifier = Modifier.padding(
                top = MaterialTheme.dimens.space24,
                start = MaterialTheme.dimens.space24
            )
        ) {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.dimens.icon32)
                    .padding(end = MaterialTheme.dimens.space4),
                painter = painterResource(id = org.the_chance.design_system.R.drawable.icon_cart),
                contentDescription = stringResource(org.the_chance.design_system.R.string.title_icon),
                tint = Color.White
            )
            Text(
                style = Typography.displayMedium.copy(color = Color.White),
                text = stringResource(org.the_chance.design_system.R.string.honey)
            )
            Text(
                text = stringResource(org.the_chance.design_system.R.string.mart),
                style = Typography.displayMedium.copy(color = Color.Black)

            )
        }
    }
}


//@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
//@Composable
//fun LoginPreview() {
//    LoginScreen()
//}