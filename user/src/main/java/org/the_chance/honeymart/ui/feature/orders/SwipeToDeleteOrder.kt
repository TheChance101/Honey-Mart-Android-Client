package org.the_chance.honeymart.ui.feature.orders

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.the_chance.honeymart.ui.feature.uistate.OrderStates

abstract class SwipeToDeleteOrder(private val drawableType: OrderStates) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
            .addActionIcon(
                when (drawableType) {
                    OrderStates.PROCESSING -> org.the_chance.design_system.R.drawable.ic_cancel
                    OrderStates.DONE -> org.the_chance.design_system.R.drawable.cart_delete
                    OrderStates.CANCELED -> org.the_chance.design_system.R.drawable.cart_delete
                }
            )
            .create()
            .decorate()
        Log.e("TAG","Update Object : ${drawableType.name}")
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
