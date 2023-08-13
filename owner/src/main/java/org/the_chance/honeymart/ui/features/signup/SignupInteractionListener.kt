package org.the_chance.honeymart.ui.features.signup

interface SignupInteractionListener {
    fun onClickLogin()
    fun onClickSignup()
    fun onFullNameInputChange(fullName: CharSequence)
    fun onEmailInputChange(email: CharSequence)
    fun onPasswordInputChanged(password: CharSequence)
    fun onConfirmPasswordChanged(confirmPassword: CharSequence)
}