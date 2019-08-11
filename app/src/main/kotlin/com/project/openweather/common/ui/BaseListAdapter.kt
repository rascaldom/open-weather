package com.project.openweather.common.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter {

    abstract class Adapter<T: Any, D: ViewDataBinding>(
        @LayoutRes private val layoutId: Int,
        private val variableId: Int? = null) : RecyclerView.Adapter<ViewHolder<D>>() {

        private val items = mutableListOf<T>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object : ViewHolder<D>(
            layoutId = layoutId,
            parent = parent,
            variableId = variableId
        ) {}

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder<D>, position: Int) {
            holder.onBindViewHolder(items[position])
        }

        fun setItems(items: List<T>?) {
            items?.let {
                this.items.run {
                    clear()
                    addAll(it)
                }
            }
        }
    }

    abstract class ViewHolder<D: ViewDataBinding>(
        @LayoutRes layoutId: Int,
        parent: ViewGroup,
        private val variableId: Int?) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    ) {

        private val binding: D = DataBindingUtil.bind(itemView)!!

        fun onBindViewHolder(item: Any?) {
            try {
                variableId?.let {
                    binding.setVariable(it, item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}