package org.the_chance.honeymart.ui.features.signup.market_info

/**
 * Created by Aziza Helmy on 8/9/2023.
 */
interface MarketInfoInteractionsListener {

    fun onClickSendButton()
    fun onMarketNameInputChange(marketName: CharSequence)
    fun onMarketAddressInputChange(address: CharSequence)
    fun onDescriptionInputChanged(description: CharSequence)
}