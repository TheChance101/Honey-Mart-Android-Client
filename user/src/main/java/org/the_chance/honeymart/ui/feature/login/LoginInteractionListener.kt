package org.the_chance.honeymart.ui.feature.login

interface LoginInteractionListener {

    fun onLoginClick()
    fun onEmailInputChange(email: CharSequence)
    fun onPasswordInputChanged(password: CharSequence)

}