package org.the_chance.honeymart.ui.feature.signup

interface SignupInteractionListener {
    fun onClickLogin()
    fun onClickSignup()
    fun onClickContinue()
    fun onFullNameInputChange(fullName: CharSequence)
    fun onEmailInputChange(email: CharSequence)
    fun onPasswordInputChanged(password: CharSequence)
    fun onConfirmPasswordChanged(confirmPassword: CharSequence)
}