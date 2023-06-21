package org.the_chance.honeymart.ui.feature.orders

import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.the_chance.user.R

class SwipeToDeleteOrder(
    private val context: Context,
    private val onSwipeCallback: (Int) -> Unit
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon: Drawable? =
        ContextCompat.getDrawable(context, org.the_chance.design_system.R.drawable.ic_password)
    private val deleteBackground: Drawable? =
        ContextCompat.getDrawable(context, org.the_chance.design_system.R.drawable.ic_password)

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        showDeleteConfirmationDialog(context) { confirmed ->
            if (confirmed) {
                onSwipeCallback(position)
            } else {
                // Revert the item back to its default position
            }
        }
    }


    private fun showDeleteConfirmationDialog(
        context: Context,
        callback: (Boolean) -> Unit
    ) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_order_dialog)
        val btnCancel = dialog.findViewById<Button>(R.id.button_cancel)
        val btnSure = dialog.findViewById<Button>(R.id.button_sure)
        val textDialog = dialog.findViewById<TextView>(R.id.text_dialog)
        btnSure.setOnClickListener {
            callback(true) // User confirmed deletion
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            callback(false) // User canceled deletion
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView

        // Draw the delete background
        deleteBackground?.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        deleteBackground?.draw(c)

        // Draw the delete icon
        deleteIcon?.let {
            val iconMargin = (itemView.height - it.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - it.intrinsicHeight) / 2
            val iconBottom = iconTop + it.intrinsicHeight

            if (dX > 0) {
                val iconLeft = itemView.left - iconMargin - it.intrinsicWidth
                val iconRight = itemView.left - iconMargin
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            } else {
                val iconLeft = itemView.right + iconMargin
                val iconRight = itemView.right + iconMargin + it.intrinsicWidth
                it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            }

            it.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
