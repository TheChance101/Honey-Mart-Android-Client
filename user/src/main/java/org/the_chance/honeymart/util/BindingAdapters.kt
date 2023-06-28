package org.the_chance.honeymart.util

import android.app.UiModeManager
import android.content.Context
import android.icu.text.DecimalFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import org.the_chance.design_system.R
import org.the_chance.honeymart.domain.util.ErrorHandler
import org.the_chance.honeymart.domain.util.ValidationState
import org.the_chance.honeymart.ui.feature.uistate.OrderStates
import org.the_chance.ui.BaseAdapter

@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) {
    (view.adapter as BaseAdapter<T>?)?.setItems(items ?: emptyList())
}

@BindingAdapter(value = ["app:recyclerItemsByCount", "app:recyclerItemCount"])
fun <T> setRecyclerItemsByCount(view: RecyclerView, items: List<T>?, count: Int) {
    (view.adapter as BaseAdapter<T>?)?.setItems(
        items?.subList(0, items.size.coerceAtMost(count)) ?: emptyList()
    )
}

@BindingAdapter("app:showIfTrue")
fun showIfTrue(view: View, condition: Boolean) {
    view.isVisible = condition
}

@BindingAdapter("app:changeChipColorForOrderProcessing")
fun changeChipColorIfProcessingSelected(chip: Chip, orderStates: OrderStates) {
    val context = chip.context
    val uiManager =
        context.applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    when (uiManager.nightMode) {
        UiModeManager.MODE_NIGHT_NO -> {
            when (orderStates) {
                OrderStates.PROCESSING -> {
                    val textColor = ContextCompat.getColor(context, R.color.white)
                    chip.setChipBackgroundColorResource(R.color.primary_100)
                    chip.setTextColor(textColor)
                }

                else -> {
                    val textColor = ContextCompat.getColor(context, R.color.primary_100)
                    chip.setChipBackgroundColorResource(R.color.white)
                    chip.setTextColor(textColor)
                }
            }
        }

        UiModeManager.MODE_NIGHT_YES -> {
            when (orderStates) {
                OrderStates.PROCESSING -> {
                    val textColor = ContextCompat.getColor(context, R.color.dark_background_300)
                    chip.setChipBackgroundColorResource(R.color.primary_100)
                    chip.setTextColor(textColor)
                }

                else -> {
                    val textColor = ContextCompat.getColor(context, R.color.primary_100)
                    chip.setChipBackgroundColorResource(R.color.dark_background_300)
                    chip.setTextColor(textColor)
                }
            }
        }
    }
}

@BindingAdapter("app:changeChipColorForOrderDone")
fun changeChipColorIfDoneSelected(chip: Chip, orderStates: OrderStates) {
    val context = chip.context
    val uiManager =
        context.applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    when (uiManager.nightMode) {
        UiModeManager.MODE_NIGHT_NO -> {
            when (orderStates) {
                OrderStates.DONE -> {
                    val textColor = ContextCompat.getColor(context, R.color.white)
                    chip.setChipBackgroundColorResource(R.color.primary_100)
                    chip.setTextColor(textColor)
                }

                else -> {
                    val textColor = ContextCompat.getColor(context, R.color.primary_100)
                    chip.setChipBackgroundColorResource(R.color.white)
                    chip.setTextColor(textColor)
                }
            }
        }

        UiModeManager.MODE_NIGHT_YES -> {
            when (orderStates) {
                OrderStates.DONE -> {
                    val textColor = ContextCompat.getColor(context, R.color.dark_background_300)
                    chip.setChipBackgroundColorResource(R.color.primary_100)
                    chip.setTextColor(textColor)
                }

                else -> {
                    val textColor = ContextCompat.getColor(context, R.color.primary_100)
                    chip.setChipBackgroundColorResource(R.color.dark_background_300)
                    chip.setTextColor(textColor)
                }
            }
        }
    }
}

@BindingAdapter("app:changeChipColorForOrderCanceled")
fun changeChipColorIfCanceledSelected(chip: Chip, orderStates: OrderStates) {
    val context = chip.context
    val uiManager =
        context.applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    when (uiManager.nightMode) {
        UiModeManager.MODE_NIGHT_NO -> {
            when (orderStates) {
                OrderStates.CANCELED -> {
                    val textColor = ContextCompat.getColor(context, R.color.white)
                    chip.setChipBackgroundColorResource(R.color.primary_100)
                    chip.setTextColor(textColor)
                }

                else -> {
                    val textColor = ContextCompat.getColor(context, R.color.primary_100)
                    chip.setChipBackgroundColorResource(R.color.white)
                    chip.setTextColor(textColor)
                }
            }
        }

        UiModeManager.MODE_NIGHT_YES -> {
            when (orderStates) {
                OrderStates.CANCELED -> {
                    val textColor = ContextCompat.getColor(context, R.color.dark_background_300)
                    chip.setChipBackgroundColorResource(R.color.primary_100)
                    chip.setTextColor(textColor)
                }

                else -> {
                    val textColor = ContextCompat.getColor(context, R.color.primary_100)
                    chip.setChipBackgroundColorResource(R.color.dark_background_300)
                    chip.setTextColor(textColor)
                }
            }
        }
    }
}

@BindingAdapter("app:placeHolderText")
fun setOrderPlaceHolderText(view: TextView, orderStates: OrderStates) {
    when (orderStates) {
        OrderStates.PROCESSING -> view.text =
            view.context.getString(R.string.you_dont_have_any_orders)

        OrderStates.DONE -> view.text =
            view.context.getString(R.string.you_dont_have_any_completed_orders)

        OrderStates.CANCELED -> view.text =
            view.context.getString(R.string.you_don_t_have_any_canceled_orders)
    }
}

@BindingAdapter("app:orderDialogText")
fun setOrderDialogText(view: TextView, orderStates: OrderStates) {
    when (orderStates) {
        OrderStates.PROCESSING -> view.text =
            view.context.getString(R.string.order_dialog_Cancel_Text)

        OrderStates.CANCELED -> view.text =
            view.context.getString(R.string.order_dialog_Delete_Text)

        else -> {}
    }
}

@BindingAdapter(value = ["app:showIfNoItems", "app:hideIfLoading"])
fun <T> showIfEmpty(view: View, items: List<T>, condition: Boolean) {
    view.isVisible = condition == false && items.isEmpty()
}

@BindingAdapter("app:showIfNotEmpty")
fun <T> showIfNotEmpty(view: View, items: List<T>) {
    view.isVisible = items.isNotEmpty()
}

@BindingAdapter(value = ["app:showIfFirsTrue", "app:showIfSecondTrue"])
fun showIfBothLoading(view: View, condition1: Boolean, condition2: Boolean) {
    if (!condition1 && !condition2) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["app:showState"])
fun showState(textView: TextView, state: Int) {
    val context = textView.context
    when (state) {
        1 -> textView.text = context.getString(R.string.Processing)
        2 -> textView.text = context.getString(R.string.Done)
        3 -> textView.text = context.getString(R.string.canceled)
        4 -> textView.text = context.getString(R.string.deleted)
    }
}

@BindingAdapter("app:changeIfSelected")
fun changeIfSelected(view: View, isSelected: Boolean) {
    val context = view.context
    val uiManager =
        context.applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    when (uiManager.nightMode) {
        UiModeManager.MODE_NIGHT_NO -> {
            when (view) {
                is CardView -> {
                    val colorRes = if (isSelected) R.color.primary_100 else R.color.white_100
                    val color = ContextCompat.getColor(context, colorRes)
                    view.setCardBackgroundColor(color)
                }

                is ShapeableImageView -> {
                    val drawableRes =
                        if (isSelected) R.drawable.icon_category_white else R.drawable.icon_category
                    val drawable = ContextCompat.getDrawable(context, drawableRes)
                    view.setImageDrawable(drawable)
                }

                is MaterialTextView -> {
                    val colorRes = if (isSelected) R.color.primary_100 else R.color.black_37
                    val color = ContextCompat.getColor(context, colorRes)
                    view.setTextColor(color)
                }
            }
        }
        UiModeManager.MODE_NIGHT_YES ->{
            when (view) {
                is CardView -> {
                    val colorRes = if (isSelected) R.color.primary_100 else R.color.dark_background_400
                    val color = ContextCompat.getColor(context, colorRes)
                    view.setCardBackgroundColor(color)
                }

                is ShapeableImageView -> {
                    val drawableRes =
                        if (isSelected) R.drawable.icon_category_white else R.drawable.icon_category
                    val drawable = ContextCompat.getDrawable(context, drawableRes)
                    view.setImageDrawable(drawable)
                }

                is MaterialTextView -> {
                    val colorRes = if (isSelected) R.color.primary_100 else R.color.dark_text_87
                    val color = ContextCompat.getColor(context, colorRes)
                    view.setTextColor(color)
                }
            }
        }
    }
    }

@BindingAdapter("app:changeColorIfSelected")
fun changeColorIfSelected(view: View, isFavorite: Boolean) {
    val context = view.context
    val uiManager =
        context.applicationContext.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    when (uiManager.nightMode) {
        UiModeManager.MODE_NIGHT_NO -> {
            when (view) {
                is CardView -> {
                    val colorRes = if (isFavorite) R.color.white else R.color.primary_100
                    val color = ContextCompat.getColor(context, colorRes)
                    view.setCardBackgroundColor(color)
                }

                is ShapeableImageView -> {
                    val drawableRes =
                        if (isFavorite) R.drawable.icon_favorite_selected else R.drawable.icon_favorite_unselected
                    val drawable = ContextCompat.getDrawable(context, drawableRes)
                    view.setImageDrawable(drawable)
                }
            }
        }

        UiModeManager.MODE_NIGHT_YES -> {
            when (view) {
                is CardView -> {
                    val colorRes =
                        if (isFavorite) R.color.dark_background_300 else R.color.primary_100
                    val color = ContextCompat.getColor(context, colorRes)
                    view.setCardBackgroundColor(color)
                }

                is ShapeableImageView -> {
                    val drawableRes =
                        if (isFavorite) R.drawable.icon_favorite_selected else R.drawable.icon_favorite_unselected
                    val drawable = ContextCompat.getDrawable(context, drawableRes)
                    view.setImageDrawable(drawable)
                }
            }
        }
    }
}


@BindingAdapter("scrollToPosition")
fun scrollToPosition(recyclerView: RecyclerView, position: Int) {
    recyclerView.scrollToPosition(position)
}

@BindingAdapter("app:hideIfLoading")
fun hideIfLoading(view: View, condition: Boolean) {
    view.isVisible = !condition
}




@BindingAdapter("app:disableIfLoading")
fun disableIfLoading(view: View, isLoading: Boolean) {
    view.isEnabled = !isLoading
}

@BindingAdapter(value = ["app:errorState", "app:validationState"])
fun setValidationState(
    textInputLayout: TextInputLayout,
    error: ErrorHandler?,
    validationState: ValidationState,
) {
    val context = textInputLayout.context

    when (validationState) {
        ValidationState.INVALID_PASSWORD -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        ValidationState.INVALID_EMAIL -> {
            textInputLayout.error = context.getString(handleValidation(validationState))
        }

        ValidationState.INVALID_FULL_NAME -> {
            textInputLayout.error = context.getString(handleValidation(validationState))
        }

        ValidationState.INVALID_CONFIRM_PASSWORD -> {
            textInputLayout.error = context.getString(handleValidation(validationState))
        }

        ValidationState.BLANK_EMAIL -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        ValidationState.BLANK_FULL_NAME -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        ValidationState.BLANK_PASSWORD -> {
            textInputLayout.error = context.getString(handleValidation(validationState))


        }

        ValidationState.INVALID_PASSWORD_LENGTH -> {
            textInputLayout.error = context.getString(handleValidation(validationState))

        }

        else -> {
            if (error != null) {
                error.let {
                    when (error) {
                        is ErrorHandler.AlreadyExist -> {
                            textInputLayout.error =
                                textInputLayout.context.getString(R.string.email_exist)
                        }

                        is ErrorHandler.EmailIsExist -> {
                            val message = "Email already exist"
                            Snackbar.make(textInputLayout, message, Snackbar.LENGTH_LONG).show()
                        }

                        else -> {
                            textInputLayout.error = null
                            textInputLayout.isErrorEnabled = false
                        }
                    }
                }
            } else {
                textInputLayout.error = null
                textInputLayout.isErrorEnabled = false
            }
        }
    }
}

@BindingAdapter("app:loadImage")
fun bindImage(image: ImageView, imageURL: String?) {
    if (imageURL.isNullOrEmpty()) {
        image.setImageResource(R.drawable.product_error_placeholder)
    } else {
        image.load(imageURL) {
            placeholder(R.drawable.loading)
            error(R.drawable.product_error_placeholder)
            crossfade(true)
            crossfade(1000)
        }
    }
}

@BindingAdapter("FormatCurrency")
fun formatCurrencyWithNearestFraction(View:TextView, amount: Double) {
    val decimalFormat = DecimalFormat("#,##0.0'$'")
    val formattedAmount = decimalFormat.format(amount)
   View.text = formattedAmount
}


@BindingAdapter(value = ["app:loadingCartState", "app:disableIfNoQuantity"])
fun loadingCartState(button: MaterialButton, isLoading: Boolean, quantity: Int?) {
    if (quantity != null) {
        if (quantity > 0) {
            button.isEnabled = !isLoading
        } else {
            button.isEnabled = false
        }
        button.text = if (isLoading) "" else button.context.getString(R.string.add_to_cart)
        val icon = if (isLoading) null else AppCompatResources.getDrawable(
            button.context,
            R.drawable.icon_add_to_cart
        )
        button.icon = icon
    }
}

@BindingAdapter("app:handleSummation")
fun handleSummation(text: TextView, count: Int) {
    text.text = if (count > 1) "$count items" else "$count item"
}

@BindingAdapter("app:errorState")
fun setError(view: View, error: ErrorHandler?) {
    error?.let {
        if (error is ErrorHandler.NoConnection) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}
