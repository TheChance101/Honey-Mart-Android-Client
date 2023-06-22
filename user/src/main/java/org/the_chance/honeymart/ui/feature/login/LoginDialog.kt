package org.the_chance.honeymart.ui.feature.login


interface LoginDialog {

    fun showDialog()

    val dialog: LoginDialogFragment
        get() = LoginDialogFragment()


}