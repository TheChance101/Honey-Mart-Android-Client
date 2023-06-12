package org.the_chance.honeymart.ui.util

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.the_chance.ui.BaseAdapter
import org.the_chance.design_system.R


@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

@BindingAdapter("app:showIfTrue")
fun showIfTrue(view: View, condition: Boolean) {
    view.isVisible = condition
}
@BindingAdapter("app:isSelectedCategory")
fun isSelectedCategory(view: View, selected: Boolean) {
    if (selected) {
        view.setBackgroundResource(R.color.primary_100)
    } else {
        view.setBackgroundResource(R.color.white_100)
    }
}