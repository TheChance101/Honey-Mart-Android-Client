package org.the_chance.honeymart.ui.feature.order_details

interface OrderDetailsInteractionListener {
    fun onClickOrder(productId: Long)
    fun onClickSubmitReview()
    fun onClickAddReview(productId: Long)
    fun onDismissAddReviewSheet()
    fun onRatingChange(rating: Float)
    fun onReviewChange(review: String)
}