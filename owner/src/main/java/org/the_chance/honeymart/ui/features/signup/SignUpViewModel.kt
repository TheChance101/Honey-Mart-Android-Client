package org.the_chance.honeymart.ui.features.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import org.the_chance.honeymart.domain.usecase.AddMarketImagesUseCase
import org.the_chance.honeymart.domain.usecase.CreateMarketUseCase
import org.the_chance.honeymart.domain.usecase.CreateOwnerAccountUseCase
import org.the_chance.honeymart.domain.usecase.ValidateMarketFieldsUseCase
import org.the_chance.honeymart.domain.usecase.ValidateSignupFieldsUseCase
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.base.BaseViewModel
import org.the_chance.honeymart.ui.features.signup.market_info.MarketInfoInteractionsListener
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createOwnerAccount: CreateOwnerAccountUseCase,
    private val validateSignupFields: ValidateSignupFieldsUseCase,
    private val createMarketUseCase: CreateMarketUseCase,
    private val addMarketImagesUseCase: AddMarketImagesUseCase,
    private val validateMarketFieldsUseCase: ValidateMarketFieldsUseCase
) : BaseViewModel<SignupUiState, SignupUiEffect>(SignupUiState()),
    SignupInteractionListener,
    MarketInfoInteractionsListener {

    override val TAG: String = this::class.simpleName.toString()

    override fun onClickLogin() {
        effectActionExecutor(_effect, SignupUiEffect.ClickLoginEffect)
    }

    override fun onClickContinue() {
        val validationSignupFieldsState = state.value.emailState.isValid &&
                state.value.passwordState.isValid &&
                state.value.fullNameState.isValid &&
                state.value.confirmPasswordState.isValid

        if (validationSignupFieldsState) {
            addOwner(
                fullName = state.value.fullNameState.value,
                email = state.value.emailState.value,
                password = state.value.passwordState.value
            )
        } else {
            effectActionExecutor(_effect, SignupUiEffect.ShowValidationToast)
        }
    }

    private fun addOwner(
        fullName: String,
        email: String,
        password: String,
    ) {
        _state.update { it.copy(isLoading = true) }
        tryToExecute(
            {
                createOwnerAccount(
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
        _state.update { it.copy(isLoading = false, isOwnerAccountCreated = isSignUp) }
    }

    private fun onCreateOwnerAccountError(error: ErrorHandler) {
        _state.update { it.copy(isLoading = false, error = error) }
    }

    override fun onFullNameInputChange(fullName: CharSequence) {
        val fullNameState = validateSignupFields.validationFullName(
            fullName.trim().toString()
        )
        _state.update {
            it.copy(
                fullNameState = FieldState(
                    errorState = when (fullNameState) {
                        ValidationState.BLANK_FULL_NAME -> "name shouldn't be empty"
                        ValidationState.INVALID_FULL_NAME -> "Invalid name"
                        else -> ""
                    },
                    value = fullName.toString(),
                    isValid = fullNameState == ValidationState.VALID_FULL_NAME
                ),
            )
        }
    }

    override fun onEmailInputChange(email: CharSequence) {
        val emailState = validateSignupFields.validateEmail(email.trim().toString())
        _state.update {
            it.copy(
                emailState = FieldState(
                    errorState = when (emailState) {
                        ValidationState.BLANK_EMAIL -> "email shouldn't be empty"
                        ValidationState.INVALID_EMAIL -> "Invalid email"
                        else -> ""
                    },
                    value = email.toString(),
                    isValid = emailState == ValidationState.VALID_EMAIL
                ),
            )
        }
    }

    override fun onPasswordInputChanged(password: CharSequence) {
        val passwordState = validateSignupFields.validationPassword(password.toString())
        _state.update {
            it.copy(
                passwordState = FieldState(
                    errorState = when (passwordState) {
                        ValidationState.BLANK_PASSWORD -> "Password shouldn't be empty"
                        ValidationState.INVALID_PASSWORD_LENGTH -> "Password length must be at least 8"
                        ValidationState.PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER -> "Please write at least 1 special character"
                        ValidationState.PASSWORD_REGEX_ERROR_LETTER -> "Please write at least 1 letter"
                        ValidationState.PASSWORD_REGEX_ERROR_DIGIT -> "Please write at least 1 digit"
                        else -> ""
                    },
                    value = password.toString(),
                    isValid = passwordState == ValidationState.VALID_PASSWORD
                ),
            )
        }
    }

    override fun onConfirmPasswordChanged(confirmPassword: CharSequence) {
        val passwordState = validateSignupFields.validateConfirmPassword(
            state.value.passwordState.value,
            confirmPassword.toString()
        )
        if (!passwordState) {
            _state.update {
                it.copy(
                    confirmPasswordState = FieldState(
                        errorState = "password doesn't match",
                        value = confirmPassword.toString(),
                        isValid = false
                    )
                )
            }
        } else {
            _state.update {
                it.copy(
                    confirmPasswordState = FieldState(
                        errorState = "",
                        value = confirmPassword.toString(),
                        isValid = true
                    )
                )
            }
        }
    }


    override fun onClickSendButton() {
        val market = state.value.marketInfoUiState
        val marketFieldsValidationState =
            market.marketNameState.isValid && market.marketAddressState.isValid
                    && market.marketDescriptionState.isValid && !market.isMarketImagesEmpty

        if (marketFieldsValidationState) {
            createMarket(
                marketName = market.marketNameState.value,
                marketAddress = market.marketAddressState.value,
                marketDescription = market.marketDescriptionState.value,
                ownerId = 4
            )
        } else {
            effectActionExecutor(_effect, SignupUiEffect.ShowValidationToast)
        }
    }

    private fun createMarket(
        marketName: String,
        marketAddress: String,
        marketDescription: String,
        ownerId: Long,
    ) {
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    isLoading = true
                )
            )
        }
        tryToExecute(
            {
                createMarketUseCase(
                    marketName = marketName,
                    marketAddress = marketAddress,
                    marketDescription = marketDescription,
                    ownerId = ownerId
                )
            },
            ::onCreateMarketSuccess,
            ::onCreateMarketError
        )
    }

    private fun onCreateMarketError(errorHandler: ErrorHandler) {
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    isLoading = false,
                    error = errorHandler
                )
            )
        }
    }

    private fun onCreateMarketSuccess(isMarketCreated: Boolean) {
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    isMarketCreated = isMarketCreated
                )
            )
        }
        addMarketImages(4, state.value.marketInfoUiState.images)
    }

    override fun onMarketNameInputChange(marketName: CharSequence) {
        val marketNameState = validateMarketFieldsUseCase.validateMarketName(
            marketName.trim().toString()
        )
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    marketNameState =
                    FieldState(
                        errorState = when (marketNameState) {
                            ValidationState.BLANK_MARKET_NAME -> "market name shouldn't be empty"
                            ValidationState.INVALID_MARKET_NAME -> "Invalid market name"
                            else -> ""
                        },
                        value = marketName.toString(),
                        isValid = marketNameState == ValidationState.VALID_MARKET_NAME
                    ),
                )
            )
        }
    }

    override fun onMarketAddressInputChange(address: CharSequence) {
        val marketAddressState = validateMarketFieldsUseCase.validateMarketAddress(
            address.trim().toString()
        )
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    marketAddressState =
                    FieldState(
                        errorState =
                        if (marketAddressState == ValidationState.BLANK_MARKET_ADDRESS)
                            "market address shouldn't be empty"
                        else
                            "",
                        value = address.toString(),
                        isValid = marketAddressState == ValidationState.VALID_MARKET_ADDRESS
                    ),
                )
            )
        }
    }

    override fun onMarketDescriptionInputChanged(description: CharSequence) {
        val marketDescriptionState = validateMarketFieldsUseCase.validateMarketDescription(
            description.trim().toString()
        )
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    marketDescriptionState =
                    FieldState(
                        errorState = when (marketDescriptionState) {
                            ValidationState.BLANK_MARKET_DESCRIPTION -> "market description shouldn't be empty"
                            ValidationState.SHORT_MARKET_DESCRIPTION -> "market description should be 20 letter at least"
                            else -> ""
                        },
                        value = description.toString(),
                        isValid = marketDescriptionState == ValidationState.VALID_MARKET_DESCRIPTION
                    ),
                )
            )
        }
    }


    override fun addMarketImages(marketId: Long, images: List<ByteArray>) {
        tryToExecute(
            {
                addMarketImagesUseCase(
                    marketId = marketId,
                    marketImages = images
                )
            },
            ::onAddMarketImagesSuccess,
            ::onAddMarketImagesError
        )
    }

    private fun onAddMarketImagesError(errorHandler: ErrorHandler) {
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    isLoading = false,
                    error = errorHandler
                )
            )
        }
    }

    private fun onAddMarketImagesSuccess(s: String) {
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    isLoading = false,
                )
            )
        }
        effectActionExecutor(_effect, SignupUiEffect.NavigateToApproveScreenEffect)
    }

    override fun onImagesSelected(uris: List<ByteArray>) {
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    images = uris,
                    isMarketImagesEmpty = false
                )
            )
        }
    }

    override fun onClickRemoveSelectedImage(imageUri: ByteArray) {
        val updatedImages = _state.value.marketInfoUiState.images.toMutableList()
        updatedImages.remove(imageUri)
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(images = updatedImages)
            )
        }
        checkIsImagesEmpty(state.value.marketInfoUiState.images)
    }

    private fun checkIsImagesEmpty(marketImages: List<ByteArray>) {
        _state.update {
            it.copy(
                marketInfoUiState = state.value.marketInfoUiState.copy(
                    isMarketImagesEmpty = marketImages.isEmpty()
                )
            )
        }
    }

}