package org.the_chance.honeymart.ui.features.login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.LoginOwnerUseCase
import org.the_chance.honeymart.domain.usecase.ValidateEmailUseCase
import org.the_chance.honeymart.domain.usecase.ValidatePasswordUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginOwnerUseCase: LoginOwnerUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<LoginUiState, LoginUiEffect>(LoginUiState()),
    LoginInteractionListener {

    override val TAG: String = this::class.java.simpleName
    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { loginOwnerUseCase(password = password, email = email) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    private fun onLoginSuccess(validationState: ValidationState) {
        if (validationState == ValidationState.SUCCESS)
            effectActionExecutor(_effect, LoginUiEffect.ClickLoginEffect)
        _state.update {
            it.copy(
                isLoading = false, error = null,
                validationState = validationState
            )
        }
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error,
                emailState = ValidationState.INVALID_EMAIL,
                passwordState = ValidationState.INVALID_PASSWORD
            )
        }
    }


    override fun onClickLogin() {
        if (_state.value.emailState == ValidationState.VALID_EMAIL &&
            _state.value.passwordState == ValidationState.VALID_PASSWORD
        ) {
            login(_state.value.email.trim(), _state.value.password.trim())
        } else {
            effectActionExecutor(_effect, LoginUiEffect.ShowToastEffect)
        }
    }


    override fun onClickSignup() {
        effectActionExecutor(_effect, LoginUiEffect.ClickSignUpEffect)
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.trim().toString())
        _state.update { it.copy(emailState = emailState, email = email.toString()) }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validatePassword(password.trim().toString())
        _state.update { it.copy(passwordState = passwordState, password = password.toString()) }
    }
}