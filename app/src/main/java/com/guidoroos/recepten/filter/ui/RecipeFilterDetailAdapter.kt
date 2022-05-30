package com.guidoroos.recepten.filter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guidoroos.recepten.databinding.FilterDetailItemBinding
import com.guidoroos.recepten.filter.model.FilterItem


class RecipeFilterDetailAdapter(private val listener: FilterDetailItemListener) : ListAdapter<FilterItem, RecipeFilterDetailAdapter.ViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    class ViewHolder(private val binding: FilterDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterItem, listener: FilterDetailItemListener) {
            binding.filterItem = item
            binding.clickListener =listener
        }
        companion object {
            fun from (parent: ViewGroup): ViewHolder {
                val binding =
                    FilterDetailItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FilterItem>() {
        override fun areItemsTheSame(oldItem: FilterItem, newItem: FilterItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: FilterItem, newItem: FilterItem) =
            oldItem.filterItemName == newItem.filterItemName
    }

    class FilterDetailItemListener(val clickListener: (filterItem:FilterItem) -> Unit) {
        fun onClick(filterItem: FilterItem) = clickListener(filterItem)
    }
}