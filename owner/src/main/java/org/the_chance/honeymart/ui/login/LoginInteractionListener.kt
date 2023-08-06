package org.the_chance.honeymart.ui.login

interface LoginInteractionListener {

    fun onClickLogin()
    fun onEmailInputChange(email: CharSequence)
    fun onPasswordInputChanged(password: CharSequence)
    fun onClickSignup()
}