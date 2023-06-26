package org.the_chance.honeymart.ui.feature.login

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
import org.the_chance.honeymart.domain.util.AlreadyExistException
import org.the_chance.honeymart.domain.util.AuthenticationException
import org.the_chance.honeymart.domain.util.EmailIsExistException
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ForbiddenException
import org.the_chance.honeymart.domain.util.GeneralException
import org.the_chance.honeymart.domain.util.InternalServerException
import org.the_chance.honeymart.domain.util.InvalidDataException
import org.the_chance.honeymart.domain.util.NetworkException
import org.the_chance.honeymart.domain.util.NoConnectionException
import org.the_chance.honeymart.domain.util.NotFoundException
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.feature.authentication.AuthenticationUiEffect
import org.the_chance.honeymart.ui.feature.uistate.LoginUiState
import org.the_chance.honeymart.util.EventHandler
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val loginUser: LoginUserUseCase,
    private val validateEmail: ValidateEmailUseCase,
    private val validatePassword: ValidatePasswordUseCase,
) : BaseViewModel<LoginUiState, AuthenticationUiEffect>(LoginUiState()) {

    override val TAG: String = this::class.java.simpleName
    private val authData = LoginFragmentArgs.fromSavedStateHandle(savedStateHandle).authData


    private fun login(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
//        tryToExecute(
//            { loginUser(password = password, email = email) },
//            ::onLoginSuccess,
//            ::onLoginError,
//        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = loginUser(password = password, email = email)
                log("tryToExecute:$result ")
                onLoginSuccess(result)
            } catch (exception: GeneralException) {
                when (exception) {
                    is InvalidDataException -> {
                        log("tryToExecute error InvalidRegisterException: ${exception.message}")
                        onLoginError(ErrorHandler.InvalidData)
                    }

                    is NotFoundException -> {
                        log("tryToExecute error InvalidEmailException: ${exception.message}")
                        onLoginError(ErrorHandler.NotFound)
                    }

                    is AlreadyExistException -> {
                        log("tryToExecute error InvalidEmailException: ${exception.message}")
                        onLoginError(ErrorHandler.AlreadyExist)
                    }
                }
                log("tryToExecute error InvalidRegisterException: ${exception.message}")
            } catch (exception: NetworkException) {
                when (exception) {
                    is InternalServerException -> {
                        log("tryToExecute error InvalidEmailException: ${exception.message}")
                        onLoginError(ErrorHandler.ServerError)
                    }

                    is NoConnectionException -> {
                        log("tryToExecute error InvalidEmailException: ${exception.message}")
                        onLoginError(ErrorHandler.NoConnection)
                    }
                }
                log("tryToExecute error InvalidEmailException: ${exception.message}")
                onLoginError(ErrorHandler.EmailIsExist)
            } catch (exception: AuthenticationException) {
                when (exception) {
                    is EmailIsExistException -> {
                        log("tryToExecute error InvalidEmailException: ${exception.message}")
                        onLoginError(ErrorHandler.EmailIsExist)
                    }

                    is ForbiddenException -> {
                        log("tryToExecute error InvalidEmailException: ${exception.message}")
                        onLoginError(ErrorHandler.UnAuthorizedUser)
                    }
                }
                log("tryToExecute error InvalidEmailException: ${exception.message}")
            } catch (exception: IOException) {
                log("tryToExecute error IOException: ${exception.message}")
                onLoginError(ErrorHandler.NoConnection)
            } catch (exception: Exception) {
                log("tryToExecute error Exception: ${exception.message}")
                onLoginError(ErrorHandler.UnKnownError)
            }
        }

    }

    private fun onLoginSuccess(validationState: ValidationState) {
        if (validationState == ValidationState.SUCCESS) {
            viewModelScope.launch {
                _effect.emit(EventHandler(AuthenticationUiEffect.ClickLoginEffect(authData)))
            }
        }
        _state.update {
            it.copy(
                isLoading = false, error = null, validationState = validationState
            )
        }

    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }


    fun onLoginClick() {
        if (_state.value.emailState == ValidationState.VALID_EMAIL ||
            _state.value.passwordState == ValidationState.VALID_PASSWORD
        ) {
            login(_state.value.email, _state.value.password)
        }
    }

    fun onClickSignUp() {
        viewModelScope.launch {
            _effect.emit(EventHandler(AuthenticationUiEffect.ClickSignUpEffect(authData)))
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

}