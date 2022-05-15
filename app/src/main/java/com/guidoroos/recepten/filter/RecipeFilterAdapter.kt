package com.guidoroos.recepten.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guidoroos.recepten.databinding.FilterItemBinding
import com.guidoroos.recepten.filter.model.FilterType



class RecipeFilterAdapter(private val clickListener:FilterItemListener) : ListAdapter<FilterType, RecipeFilterAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, clickListener)
    }

    class ViewHolder(private val binding: FilterItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FilterType, listener:FilterItemListener) {
            binding.filterType = item
            binding.clickListener =listener
        }

        companion object {
            fun from (parent: ViewGroup): ViewHolder {
                val binding =
                    FilterItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FilterType>() {
        override fun areItemsTheSame(oldItem: FilterType, newItem: FilterType) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: FilterType, newItem: FilterType) =
            oldItem == newItem
    }

    class FilterItemListener(val clickListener: (filterType: FilterType) -> Unit) {
        fun onClick(filterType: FilterType) = clickListener(filterType)
    }
}