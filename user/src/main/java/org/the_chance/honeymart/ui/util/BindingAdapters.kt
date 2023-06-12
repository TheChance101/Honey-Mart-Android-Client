package org.the_chance.honeymart.ui.util

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.the_chance.design_system.R
import org.the_chance.ui.BaseAdapter


@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

@BindingAdapter("app:showIfTrue")
fun showIfTrue(view: View, condition: Boolean) {
    view.isVisible = condition
}

@BindingAdapter("app:selected")
fun changeIfSelected(cardView: CardView, isSelected: Boolean) {
    if (isSelected) {
        val selectedColor = ContextCompat.getColor(cardView.context, R.color.primary_100)
        cardView.setCardBackgroundColor(selectedColor)
    } else {
        val notSelectedColor = ContextCompat.getColor(cardView.context, R.color.white_100)
        cardView.setCardBackgroundColor(notSelectedColor)
    }
}

