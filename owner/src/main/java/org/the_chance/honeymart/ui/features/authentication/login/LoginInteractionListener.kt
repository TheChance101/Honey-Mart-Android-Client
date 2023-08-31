package org.the_chance.honeymart.ui.features.authentication.login

interface LoginInteractionListener {

    fun onClickLogin()
    fun onEmailInputChange(email: CharSequence)
    fun onPasswordInputChanged(password: CharSequence)
    fun onClickSignup()
}