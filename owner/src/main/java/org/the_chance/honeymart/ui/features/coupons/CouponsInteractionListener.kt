package org.the_chance.honeymart.ui.features.coupons

interface CouponsInteractionListener : ProductSearchInteractionListener,
    AddCouponInteractionListener {
    fun getMarketProducts()
    override fun onProductSearchItemClick(productId: Long)
    override fun onProductSearchTextChange(text: String)
    override fun onAddCouponClick()
    override fun onDiscountPercentageChange(discountPercentage: String)
    override fun onCouponCountChange(couponCount: String)
    override fun onClickShowDatePicker()
    override fun onDatePickerDoneClick(date: Long)
    override fun onDatePickerDismiss()
}

interface ProductSearchInteractionListener {
    fun onProductSearchItemClick(productId: Long)
    fun onProductSearchTextChange(text: String)
}

interface AddCouponInteractionListener {
    fun onAddCouponClick()
    fun onDiscountPercentageChange(discountPercentage: String)
    fun onCouponCountChange(couponCount: String)
    fun onClickShowDatePicker()
    fun onDatePickerDoneClick(date: Long)
    fun onDatePickerDismiss()
}