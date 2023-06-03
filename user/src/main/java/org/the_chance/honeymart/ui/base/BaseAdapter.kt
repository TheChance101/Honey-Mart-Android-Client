package org.the_chance.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.the_chance.honeymart.ui.base.BaseDiffUtil

interface BaseInteractionListener
abstract class BaseAdapter<T>(
    private val listener: BaseInteractionListener,
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {

    private var items = emptyList<T>()
    abstract val layoutID: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(
            DataBindingUtil.inflate(inflater, layoutID, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is ItemViewHolder) bind(holder, position)
    }

    open fun bind(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            setVariable(BR.item, items[position])
            setVariable(BR.listener, listener)
        }
    }

    class ItemViewHolder(val binding: ViewDataBinding) : BaseViewHolder(binding)

    abstract class BaseViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = items.size

    open fun setItems(newItems: List<T>) {
        val diffResult =
            DiffUtil.calculateDiff(BaseDiffUtil(items, newItems, ::areItemsSame, ::areContentSame))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    open fun areItemsSame(oldItem: T, newItem: T): Boolean {
        return oldItem?.equals(newItem) == true
    }

    open fun areContentSame(oldPosition: T, newPosition: T) = true

}