package org.the_chance.honeymart.ui.feature.coupons

interface CouponsInteractionListener {
    fun getData()

    //fun onClickGetCoupon(couponId: Long)

    fun onClickAllCoupons()
    fun onClickValidCoupons()
    fun onClickExpiredCoupons()
}