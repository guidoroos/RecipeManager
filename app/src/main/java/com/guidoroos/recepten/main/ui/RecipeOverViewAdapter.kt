package com.guidoroos.recepten.main.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.RecipeItemBinding
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.filter.model.FilterItem


class RecipeOverViewAdapter(private val listener:RecipeOverviewItemListener):
    ListAdapter<Recipe, RecipeOverViewAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem,listener)
    }

    class ViewHolder(private val binding:RecipeItemBinding ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Recipe, listener:RecipeOverviewItemListener) {
            binding.recipe = item
            binding.listener = listener
        }
        companion object {
            fun from (parent: ViewGroup): ViewHolder {

                val binding =
                    RecipeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem:Recipe , newItem:Recipe  ) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem:Recipe  , newItem:Recipe  ) =
            oldItem == newItem
    }

    class RecipeOverviewItemListener(val clickListener: (recipe: Recipe) -> Unit) {
        fun onClick(recipe: Recipe) = clickListener(recipe)
    }
}