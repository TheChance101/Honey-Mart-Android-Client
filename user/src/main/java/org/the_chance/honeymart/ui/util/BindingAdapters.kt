package org.the_chance.honeymart.ui.util

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
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

@BindingAdapter("app:changeColor")
fun changeIfSelected(cardView: CardView, isSelected: Boolean) {
    if (isSelected) {
        val selectedColor = ContextCompat.getColor(cardView.context, R.color.primary_100)
        cardView.setCardBackgroundColor(selectedColor)
    } else {
        val notSelectedColor = ContextCompat.getColor(cardView.context, R.color.white_100)
        cardView.setCardBackgroundColor(notSelectedColor)
    }
}

@BindingAdapter("app:changeImage")
fun changeIfSelected(imageView: ShapeableImageView, isSelected: Boolean) {
    if (isSelected) {
        val selectedColor = ContextCompat.getDrawable(imageView.context, R.drawable.icon_category_white)
        imageView.setImageDrawable(selectedColor)
    } else {
        val notSelectedColor = ContextCompat.getDrawable(imageView.context, R.drawable.icon_category)
        imageView.setImageDrawable(notSelectedColor)
    }
}

@BindingAdapter("app:changeTextColor")
fun changeIfSelected(textView: MaterialTextView, isSelected: Boolean) {
    if (isSelected) {
        val selectedColor = ContextCompat.getColor(textView.context, R.color.primary_100)
        textView.setTextColor(selectedColor)
    } else {
        val notSelectedColor = ContextCompat.getColor(textView.context, R.color.black_60)
        textView.setTextColor(notSelectedColor)
    }
}

