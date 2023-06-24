package org.the_chance.honeymart.ui.feature.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import org.the_chance.honeymart.domain.usecase.LoginUserUseCase
import org.the_chance.honeymart.domain.usecase.ValidateEmailUseCase
import org.the_chance.honeymart.domain.usecase.ValidatePasswordUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidEmailOrPasswordException
import org.the_chance.honeymart.domain.util.InvalidRegisterException
import org.the_chance.honeymart.domain.util.NoNetworkException
import org.the_chance.honeymart.domain.util.UnAuthorizedException
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.AuthenticationUiEffect
import org.the_chance.honeymart.ui.feature.uistate.LoginUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loginUserUseCase: LoginUserUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<LoginUiState, AuthenticationUiEffect>(LoginUiState()) {

    override val TAG: String = this::class.java.simpleName
    private val authData = LoginFragmentArgs.fromSavedStateHandle(savedStateHandle).authData


    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
//        tryToExecute(
//            { loginUserUseCase(password = password, email = email) },
//            ::onLoginSuccess,
//            ::onLoginError,
//        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = loginUserUseCase(password = password, email = email)
                Log.e(TAG, "tryToExecute:$result ")
                onLoginSuccess(result)
            } catch (exception: InvalidRegisterException) {
                log("tryToExecute error InvalidRegisterException: ${exception.message}")
                onLoginError(ErrorHandler.InvalidRegister)
            } catch (exception: InvalidEmailException) {
                log("tryToExecute error InvalidEmailException: ${exception.message}")
                onLoginError(ErrorHandler.EmailIsExist)
            } catch (exception: NoNetworkException) {
                log("tryToExecute error InvalidEmailException: ${exception.message}")
                onLoginError(ErrorHandler.NoNetwork)
            } catch (exception: UnAuthorizedException) {
                log("tryToExecute error Exception: ${exception.message}")
            } catch (exception: InvalidEmailOrPasswordException) {
                onLoginError(ErrorHandler.InvalidEmailAndPassword)
                log("tryToExecute error InvalidEmailOrPasswordException: ${exception.message}")
            } catch (exception: IOException) {
                onLoginError(ErrorHandler.NoNetwork)
                log("tryToExecute error Exception: ${exception.message}, ${exception.cause}")
            }

        }
    }

    private fun onLoginSuccess(validationState: ValidationState) {
        if (validationState == ValidationState.SUCCESS) {
            viewModelScope.launch {
                _effect.emit(
                    EventHandler(
                        AuthenticationUiEffect.ClickLoginEffect(
                            authData
                        )
                    )
                )
            }
        }
        _state.update {
            it.copy(
                isLoading = false,
                error = ErrorHandler.NoError,
                validationState = validationState
            )
        }

    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }


    fun onLoginClick() {
        if (_state.value.validationState == ValidationState.SUCCESS) {
            viewModelScope.launch { login(_state.value.email, _state.value.password) }
        }

    }

    fun onEmailInputChange(email: CharSequence) {
        val emailState = validateEmail(email.toString())
        _state.update { it.copy(emailState = emailState, email = email.toString()) }
    }

    fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validatePassword(password.toString())
        _state.update { it.copy(passwordState = passwordState, password = password.toString()) }
    }

    fun onClickSignUp() {
        viewModelScope.launch {
            _effect.emit(
                EventHandler(
                    AuthenticationUiEffect.ClickSignUpEffect(
                        authData
                    )
                )
            )
        }
    }

}