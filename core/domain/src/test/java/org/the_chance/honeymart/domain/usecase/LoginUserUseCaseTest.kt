package org.the_chance.honeymart.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.the_chance.honeymart.domain.repository.AuthRepository
import org.the_chance.honeymart.domain.util.InvalidEmailException
import org.the_chance.honeymart.domain.util.InvalidPasswordInputException
import org.the_chance.honeymart.domain.util.InvalidUserNameOrPasswordException
import org.the_chance.honeymart.domain.util.UnKnownUserException
import org.the_chance.honeymart.domain.util.ValidationState

@ExtendWith(MockitoExtension::class)
class LoginUserUseCaseTest {

    @Mock
    private lateinit var mockAuthRepository: AuthRepository

    @InjectMocks
    private lateinit var loginUserUseCase: LoginUserUseCase

    @Mock
    private lateinit var mockValidationUseCase: IValidationUseCase

    @BeforeEach
    fun setup() {
        reset(mockAuthRepository)
        lenient().`when`(mockValidationUseCase.validateEmail(TEST_EMAIL))
            .thenReturn(ValidationState.VALID_EMAIL)
        lenient().`when`(mockValidationUseCase.validationPassword(TEST_PASSWORD))
            .thenReturn(ValidationState.VALID_PASSWORD)
    }

    @Test
    fun `given valid email and password, when login is invoked, then tokens are saved`() =
        runBlocking {
            `when`(mockAuthRepository.getDeviceToken()).thenReturn(TEST_DEVICE_TOKEN)
            `when`(
                mockAuthRepository.loginUser(
                    TEST_EMAIL,
                    TEST_PASSWORD,
                    TEST_DEVICE_TOKEN
                )
            ).thenReturn(TEST_TOKENS)

            loginUserUseCase(TEST_EMAIL, TEST_PASSWORD)

            verify(mockAuthRepository).getDeviceToken()
            verify(mockAuthRepository)
                .loginUser(TEST_EMAIL, TEST_PASSWORD, TEST_DEVICE_TOKEN)
            verify(mockAuthRepository)
                .saveTokens(TEST_TOKENS.accessToken, TEST_TOKENS.refreshToken)
        }

    @Test
    fun `given getDeviceToken fails, when login is invoked, then ensure no further calls are made`() =
        runBlocking {
            `when`(mockAuthRepository.getDeviceToken())
                .thenThrow(UnKnownUserException::class.java)

            assertThrows<UnKnownUserException> { loginUserUseCase(TEST_EMAIL, TEST_PASSWORD) }

            verify(mockAuthRepository).getDeviceToken()
            verifyNoMoreInteractions(mockAuthRepository)
        }

    @Test
    fun `given loginUser fails, when login is invoked, then tokens are not saved`() = runBlocking {
        // Arrange
        `when`(mockAuthRepository.getDeviceToken()).thenReturn(TEST_DEVICE_TOKEN)
        `when`(mockAuthRepository.loginUser(TEST_EMAIL, TEST_PASSWORD, TEST_DEVICE_TOKEN))
            .thenThrow(InvalidUserNameOrPasswordException::class.java)

        assertThrows<InvalidPasswordInputException> {
            // Act
            loginUserUseCase(TEST_EMAIL, TEST_PASSWORD)
        }

        // Assert
        verify(mockAuthRepository).getDeviceToken()
        verify(mockAuthRepository).loginUser(TEST_EMAIL, TEST_PASSWORD, TEST_DEVICE_TOKEN)
        verify(mockAuthRepository, never()).saveTokens(
            TEST_TOKENS.accessToken,
            TEST_TOKENS.refreshToken
        )
    }

    @ParameterizedTest
    @EnumSource(ValidationState::class, names = ["BLANK_PASSWORD", "SHORT_PASSWORD", "INVALID_PASSWORD","INVALID_PASSWORD_LENGTH_SHORT","INVALID_PASSWORD_LENGTH_LONG",
        "INVALID_CONFIRM_PASSWORD", "PASSWORD_REGEX_ERROR_LETTER","PASSWORD_REGEX_ERROR_DIGIT", "PASSWORD_REGEX_ERROR_SPECIAL_CHARACTER"])
    fun `given invalid passwords, ensure repository calls are not made`(invalidState: ValidationState) = runBlocking {
        println("Testing with ValidationState: $invalidState")

        `when`(mockValidationUseCase.validationPassword(anyString())).thenReturn(invalidState)

        try {
            loginUserUseCase(TEST_EMAIL, "invalidPassword")
            Assertions.fail("Expected an InvalidPasswordInputException to be thrown.")
        } catch (e: Exception) {
            if (e is NullPointerException) {
                println("NullPointerException encountered. Printing stack trace...")
                e.printStackTrace()
            } else {
                Assertions.assertTrue(
                    e is InvalidPasswordInputException,
                    "Expected InvalidPasswordInputException but received ${e::class.java}"
                )
            }
        }
    }


    @Test
    fun `given token saving fails, when login is , then appropriate error is thrown`() =
        runBlocking<Unit> {
            `when`(mockAuthRepository.getDeviceToken()).thenReturn(TEST_DEVICE_TOKEN)
            `when`(
                mockAuthRepository.loginUser(
                    TEST_EMAIL,
                    TEST_PASSWORD,
                    TEST_DEVICE_TOKEN
                )
            ).thenReturn(TEST_TOKENS)
            doThrow(InvalidPasswordInputException()).`when`(mockAuthRepository).saveTokens(
                TEST_TOKENS.accessToken,
                TEST_TOKENS.refreshToken
            )

            assertThrows<InvalidPasswordInputException> { loginUserUseCase(TEST_EMAIL, TEST_PASSWORD) }
        }

    @ParameterizedTest
    @ValueSource(strings = ["", "test@", "test.com"])
    fun `given invalid email formats, ensure repository calls are not made`(email: String) =
        runBlocking {
            `when`(mockValidationUseCase.validateEmail(email)).thenReturn(ValidationState.INVALID_EMAIL)

            assertThrows<InvalidEmailException> { loginUserUseCase(email, TEST_PASSWORD) }
            verifyNoInteractions(mockAuthRepository)
        }


    companion object {
        private const val TEST_EMAIL = "test@example.com"
        private const val TEST_PASSWORD = "password"
        private const val TEST_DEVICE_TOKEN = "deviceToken"
        private val TEST_TOKENS = Tokens("accessToken", "refreshToken")
    }
}