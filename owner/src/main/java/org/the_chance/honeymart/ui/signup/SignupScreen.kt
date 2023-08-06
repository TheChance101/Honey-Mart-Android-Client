package org.the_chance.honeymart.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honymart.ui.composables.HoneyFilledButton
import org.the_chance.honymart.ui.composables.HoneyTextField
import org.the_chance.honymart.ui.composables.Loading
import org.the_chance.honymart.ui.theme.Typography
import org.the_chance.honymart.ui.theme.black37
import org.the_chance.honymart.ui.theme.black60
import org.the_chance.honymart.ui.theme.black87
import org.the_chance.honymart.ui.theme.dimens
import org.the_chance.honymart.ui.theme.primary100
import org.the_chance.owner.R

@Composable
fun SignupScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    SignupContent(listener = viewModel, state = state)

}

@Composable
fun SignupContent(
    state: SignupUiState,
    listener: SignupInteractionListener,
) {
    Loading(state = state.isLoading)

    Box {
        Row {

            Image(
                painter = painterResource(id = R.drawable.image),
                contentDescription = "honey image",
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
                    text = stringResource(R.string.sign_up),
                    color = black87,
                    style = Typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.dimens.space24)
                        .align(
                            Alignment.CenterHorizontally
                        )
                )
                Text(
                    text = stringResource(R.string.create_an_account_name_your_market),
                    color = black60,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 100.dp)
                )
                HoneyTextField(
                    text = state.fullName,
                    hint = "full name",
                    iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_person),
                    onValueChange = listener::onFullNameInputChange,
                    errorMessage = when (state.fullNameState) {
                        ValidationState.BLANK_FULL_NAME -> "name cannot be blank"
                        ValidationState.INVALID_FULL_NAME -> "Invalid name"
                        else -> ""
                    },
                )
                HoneyTextField(
                    text = state.email,
                    hint = "Email",
                    iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_email),
                    onValueChange = listener::onEmailInputChange,
                    errorMessage = when (state.emailState) {
                        ValidationState.BLANK_EMAIL -> "email cannot be blank"
                        ValidationState.INVALID_EMAIL -> "Invalid email"
                        else -> ""
                    },
                )
                HoneyTextField(
                    text = state.password,
                    hint = "Password",
                    iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_password),
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
                    hint = "Confirm Password",
                    iconPainter = painterResource(org.the_chance.design_system.R.drawable.ic_password),
                    onValueChange = listener::onConfirmPasswordChanged,
                    errorMessage = when (state.confirmPasswordState) {
                        ValidationState.INVALID_CONFIRM_PASSWORD -> "Invalid confirm password"
                        else -> ""
                    }
                )


                HoneyFilledButton(
                    label = stringResource(R.string.continuo),
                    onClick = listener::onClickSignup,
                    background = primary100,
                    contentColor = Color.White,
                    modifier = Modifier.padding(
                        bottom = MaterialTheme.dimens.space24,
                        top = MaterialTheme.dimens.space32
                    )
                )


                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(R.string.alrady_have_account),
                        style = Typography.displaySmall.copy(black37),
                        textAlign = TextAlign.Center
                    )
                    TextButton(
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
                painter = painterResource(id = R.drawable.icon_cart),
                contentDescription = stringResource(R.string.title_icon),
                tint = Color.White
            )
            Text(
                style = Typography.displayMedium.copy(color = Color.White),
                text = stringResource(R.string.honey)
            )
            Text(
                text = stringResource(R.string.mart),
                style = Typography.displayMedium.copy(color = Color.Black)
            )
        }
    }
}


@Preview(name = "Tablet", device = Devices.TABLET, showSystemUi = true)
@Composable
fun signupPreview() {
    SignupScreen()
}