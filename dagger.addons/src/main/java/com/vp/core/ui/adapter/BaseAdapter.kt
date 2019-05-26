package com.vp.core.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vp.core.ui.extensions.inflateCustomView

abstract class BaseAdapter<ITEM> constructor(protected var items: List<ITEM>,
                                             private val layoutResId: Int)
    : RecyclerView.Adapter<BaseAdapter.Holder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = parent.inflateCustomView(layoutResId, attachToRoot = false)
        val viewHolder = Holder(view)
        val itemView = viewHolder.itemView
        itemView.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(itemView, adapterPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseAdapter.Holder, position: Int) {
        val item = items[position]
        holder.itemView.bind(item)
    }

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }

    fun add(item: ITEM) {
        items.toMutableList().add(item)
        notifyItemInserted(items.size)
    }

    fun remove(position: Int) {
        items.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    final override fun onViewRecycled(holder: Holder) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.itemView)
    }

    protected open fun onViewRecycled(itemView: View) {
    }

    protected open fun onItemClick(itemView: View, position: Int) {
    }

    protected open fun View.bind(item: ITEM) {
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}