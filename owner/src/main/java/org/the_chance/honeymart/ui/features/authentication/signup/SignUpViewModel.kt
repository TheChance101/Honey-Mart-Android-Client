package org.the_chance.honeymart.ui.features.authentication.signup

import arrow.optics.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.ValidateMarketFieldsUseCase
import org.the_chance.honeymart.domain.usecase.ValidateAuthenticationFieldsUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerAuthenticationManagerUseCase
import org.the_chance.honeymart.domain.usecase.usecaseManager.owner.OwnerMarketsManagerUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.MarketInfoInteractionsListener
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.error
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.image
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.isButtonEnabled
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.isLoading
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.isMarketCreated
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.isMarketImageEmpty
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.marketAddressState
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.marketDescriptionState
import org.the_chance.honeymart.ui.features.authentication.signup.marketInfo.marketNameState
import org.the_chance.honeymart.ui.util.StringDictionary
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val ownerAuthentication: OwnerAuthenticationManagerUseCase,
    private val ownerMarkets: OwnerMarketsManagerUseCase,
    private val validateSignupFields: ValidateAuthenticationFieldsUseCase,
    private val validateMarketFieldsUseCase: ValidateMarketFieldsUseCase,
    private val stringResourceImpl: StringDictionary,
) : BaseViewModel<SignupUiState, SignupUiEffect>(SignupUiState()),
    SignupInteractionListener,
    MarketInfoInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()

    // region owner registration
    override fun onClickLogin() {
        effectActionExecutor(_effect, SignupUiEffect.ClickLoginEffect)
    }

    override fun onClickContinue() {
        _state.update { it.copy { SignupUiState.isButtonEnabled set false } }
        val validationSignupFieldsState = state.value.emailState.isValid &&
                state.value.passwordState.isValid &&
                state.value.fullNameState.isValid &&
                state.value.confirmPasswordState.isValid

        if (validationSignupFieldsState) {
            createOwner(
                fullName = state.value.fullNameState.value,
                email = state.value.emailState.value,
                password = state.value.passwordState.value
            )
        } else {
            _state.update { it.copy { SignupUiState.isButtonEnabled set true } }
            showValidationToast(stringResourceImpl.requiredFieldsMessageString)
        }
    }

    private fun createOwner(
        fullName: String,
        email: String,
        password: String,
    ) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                ownerAuthentication.createOwnerAccount(
                    fullName = fullName,
                    password = password,
                    email = email,
                )
            },
            ::onCreateOwnerAccountSuccess,
            ::onCreateOwnerAccountError,
        )
    }

    private fun onCreateOwnerAccountSuccess(isSignUp: Boolean) {
        loginOwner(email = state.value.emailState.value, password = state.value.passwordState.value)
        _state.update { it.copy { SignupUiState.isOwnerAccountCreated set isSignUp } }
    }

    private fun onCreateOwnerAccountError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error, isButtonEnabled = true) }
        if (error == ErrorHandler.EmailAlreadyExist) {
            showValidationToast(
                message = stringResourceImpl.errorString.getOrDefault(
                    error,
                    ""
                )
            )
        }
        showValidationToast(
            message = stringResourceImpl.errorString.getOrDefault(
                error,
                ""
            )
        )
        log(error.toString())
    }

    private fun loginOwner(email: String, password: String) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            { ownerAuthentication.loginOwnerUseCase(email, password) },
            ::onLoginSuccess,
            ::onLoginError,
        )
    }

    private fun onLoginSuccess(unit: Unit) {
        _state.update { it.copy(isLoading = false, isButtonEnabled = true) }
    }

    private fun onLoginError(error: ErrorHandler) {
        _state.update { it.copy { SignupUiState.isButtonEnabled set true } }
        showValidationToast(
            message = stringResourceImpl.errorString.getOrDefault(
                error,
                ""
            )
        )
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validateSignupFields.validationFullName(
            fullName.trim().toString()
        )
        _state.update {
            it.copy {
                SignupUiState.fullNameState set FieldState(
                    errorState = stringResourceImpl.validationString.getOrDefault(
                        fullNameState,
                        ""
                    ),
                    value = fullName.toString(),
                    isValid = fullNameState == ValidationState.VALID_FULL_NAME
                )
            }
        }
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validateSignupFields.validateEmail(email.trim().toString())
        _state.update {
            it.copy {
                SignupUiState.emailState set FieldState(
                    errorState = stringResourceImpl.validationString.getOrDefault(
                        emailState,
                        ""
                    ),
                    value = email.toString(),
                    isValid = emailState == ValidationState.VALID_EMAIL
                )
            }
        }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validateSignupFields.validationPassword(password.toString())
        _state.update {
            it.copy {
                SignupUiState.passwordState set FieldState(
                    errorState = stringResourceImpl.validationString.getOrDefault(
                        passwordState,
                        ""
                    ),
                    value = password.toString(),
                    isValid = passwordState == ValidationState.VALID_PASSWORD
                )
            }
        }
    }

    override fun onConfirmPasswordChanged(confirmPassword: CharSequence) {
        val passwordState = validateSignupFields.validateConfirmPassword(
            _state.value.passwordState.value,
            confirmPassword.toString()
        )
        if (passwordState == ValidationState.CONFIRM_PASSWORD_DOES_NOT_MATCH) {
            _state.update {
                it.copy {
                    SignupUiState.confirmPasswordState set FieldState(
                        errorState = stringResourceImpl.validationString.getOrDefault(
                            passwordState,
                            ""
                        ),
                        value = confirmPassword.toString(),
                        isValid = false
                    )
                }
            }
        } else {
            _state.update {
                it.copy {
                    SignupUiState.confirmPasswordState set FieldState(
                        errorState = "",
                        value = confirmPassword.toString(),
                        isValid = true
                    )
                }
            }
        }
    }

// endregion

    // region market registration
    override fun onClickSendButton() {
        _state.update {
            it.copy { SignupUiState.marketInfoUiState.isButtonEnabled set false }
        }
        val market = state.value.marketInfoUiState
        val marketFieldsValidationState =
            market.marketNameState.isValid && market.marketAddressState.isValid
                    && market.marketDescriptionState.isValid && !market.isMarketImageEmpty

        if (marketFieldsValidationState) {

            createMarket(
                marketName = market.marketNameState.value,
                marketAddress = market.marketAddressState.value,
                marketDescription = market.marketDescriptionState.value,
            )
        } else {
            _state.update {
                it.copy { SignupUiState.marketInfoUiState.isButtonEnabled set true }
            }
            effectActionExecutor(_effect, SignupUiEffect.ShowValidationToast)
        }
    }

    private fun createMarket(
        marketName: String,
        marketAddress: String,
        marketDescription: String,
    ) {
        _state.update {
            it.copy { SignupUiState.marketInfoUiState.isLoading set true }
        }
        tryToExecute(
            {
                ownerMarkets.createMarketUseCase(
                    marketName = marketName,
                    marketAddress = marketAddress,
                    marketDescription = marketDescription,
                )
            },
            ::onCreateMarketSuccess,
            ::onCreateMarketError
        )
    }

    private fun onCreateMarketError(errorHandler: ErrorHandler) {
        showValidationToast(
            message = stringResourceImpl.errorString.getOrDefault(
                errorHandler,
                ""
            )
        )
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.isLoading set false
                SignupUiState.marketInfoUiState.error set errorHandler
                SignupUiState.marketInfoUiState.isButtonEnabled set true
            }
        }
    }

    private fun onCreateMarketSuccess(isMarketCreated: Boolean) {
        _state.update {
            it.copy { SignupUiState.marketInfoUiState.isMarketCreated set isMarketCreated }
        }
        addMarketImage(state.value.marketInfoUiState.image)
    }

    override fun onMarketNameInputChange(marketName: CharSequence) {
        val marketNameState = validateMarketFieldsUseCase.validateMarketName(
            marketName.trim().toString()
        )
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.marketNameState set FieldState(
                    errorState = stringResourceImpl.validationString.getOrDefault(
                        marketNameState,
                        ""
                    ),
                    value = marketName.toString(),
                    isValid = marketNameState == ValidationState.VALID_MARKET_NAME
                )
            }
        }
    }

    override fun onMarketAddressInputChange(address: CharSequence) {
        val marketAddressState = validateMarketFieldsUseCase.validateMarketAddress(
            address.trim().toString()
        )
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.marketAddressState set FieldState(
                    errorState = stringResourceImpl.validationString.getOrDefault(
                        marketAddressState,
                        ""
                    ),
                    value = address.toString(),
                    isValid = marketAddressState == ValidationState.VALID_MARKET_ADDRESS
                )
            }
        }
    }

    override fun onMarketDescriptionInputChanged(description: CharSequence) {
        val marketDescriptionState = validateMarketFieldsUseCase.validateMarketDescription(
            description.trim().toString()
        )
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.marketDescriptionState set FieldState(
                    errorState = stringResourceImpl.validationString.getOrDefault(
                        marketDescriptionState,
                        ""
                    ),
                    value = description.toString(),
                    isValid = marketDescriptionState == ValidationState.VALID_MARKET_DESCRIPTION
                )
            }
        }
    }

// endregion

    // region market image
    override fun addMarketImage(image: ByteArray) {
        tryToExecute(
            { ownerMarkets.addMarketImageUseCase(marketImage = image) },
            ::onAddMarketImageSuccess,
            ::onAddMarketImageError
        )
    }

    private fun onAddMarketImageError(errorHandler: ErrorHandler) {
        showValidationToast(
            message = stringResourceImpl.errorString.getOrDefault(
                errorHandler,
                ""
            )
        )
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.isLoading set false
                SignupUiState.marketInfoUiState.error set errorHandler
                SignupUiState.marketInfoUiState.isButtonEnabled set true
            }
        }
    }

    private fun onAddMarketImageSuccess(isMaretImageAdded: Boolean) {
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.isLoading set false
                SignupUiState.marketInfoUiState.isButtonEnabled set true
            }
        }
        listenToCheckAdminApprove()
    }

    override fun onImageSelected(uri: ByteArray) {
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.image set uri
                SignupUiState.marketInfoUiState.isMarketImageEmpty set uri.isEmpty()
            }
        }
    }

    override fun onClickRemoveSelectedImage(imageUri: ByteArray) {
        _state.update {
            it.copy {
                SignupUiState.marketInfoUiState.image set byteArrayOf()
                SignupUiState.marketInfoUiState.isMarketImageEmpty set true
            }
        }
    }

//endregion

    // region logout
    override fun onClickLogout() {
        tryToExecute(
            function = { ownerAuthentication.logOutOwnerUseCase() },
            onSuccess = { onLogoutSuccess() },
            onError = ::onLogoutError
        )
    }

    private fun onLogoutSuccess() {
        effectActionExecutor(_effect, SignupUiEffect.ClickLogoutEffect)
    }

    private fun onLogoutError(error: ErrorHandler) {
        showValidationToast(
            message = stringResourceImpl.errorString.getOrDefault(
                error,
                ""
            )
        )
        _state.update { it.copy(isLoading = false, error = error) }
    }
    // endregion

    // region Admin approve
    private fun listenToCheckAdminApprove() {
        tryToExecute(
            { ownerAuthentication.checkAdminApprove() },
            ::onCheckApproveSuccess,
            ::onCheckApproveError
        )
    }

    private fun onCheckApproveSuccess(isApproved: Boolean) {
        _state.update { it.copy(isLoading = false) }
        if (isApproved) {
            effectActionExecutor(_effect, SignupUiEffect.NavigateToCategoriesEffect)
        } else {
            effectActionExecutor(_effect, SignupUiEffect.NavigateToWaitingApproveEffect)
        }
    }

    private fun onCheckApproveError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
        showValidationToast(stringResourceImpl.errorString.getOrDefault(error, ""))
    }

    // endregion

    private fun showValidationToast(message: String) {
        _state.update {
            it.copy {
                SignupUiState.validationToast set ValidationToast(
                    isShow = true,
                    message = message
                )
            }
        }
        effectActionExecutor(_effect, SignupUiEffect.ShowValidationToast)
    }
}