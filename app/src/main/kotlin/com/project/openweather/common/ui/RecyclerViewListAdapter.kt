package com.project.openweather.common.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewListAdapter {

    abstract class Adapter<T: Any, D: ViewDataBinding>(
        @LayoutRes private val layoutId: Int,
        private val variableId: Int? = null,
        private val clickListener: ListItemClickListener? = null) : RecyclerView.Adapter<ViewHolder<D>>() {

        private val items = mutableListOf<T>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object : ViewHolder<D>(
            layoutId = layoutId,
            parent = parent,
            variableId = variableId,
            clickListener = clickListener
        ) {}

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder<D>, position: Int) {
            holder.onBindViewHolder(items[position], position)
        }

        fun setItems(items: List<T>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                }
            }
        }

        fun getItem(position: Int) = items[position]
    }

    abstract class ViewHolder<D: ViewDataBinding>(
        @LayoutRes layoutId: Int,
        parent: ViewGroup,
        private val variableId: Int?,
        private val clickListener: ListItemClickListener?) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ) {

        private val binding: D = DataBindingUtil.bind(itemView)!!

        fun onBindViewHolder(item: Any?, position: Int) {
            try {
                variableId?.let {
                    binding.setVariable(it, item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            itemView.setOnClickListener {
                clickListener?.onItemClick(it, position)
            }
        }
    }

    interface ListItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}