package com.guidoroos.recepten.recipe.ui.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guidoroos.recepten.data.IngredientViewData
import com.guidoroos.recepten.databinding.IngredientItemBinding


class IngredientViewDataAdapter(private val listener: IngredientItemListener) :
    ListAdapter<IngredientViewData, IngredientViewDataAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    class ViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IngredientViewData, listener: IngredientItemListener) {
            binding.listener = listener
            binding.data = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val binding =
                    IngredientItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(binding)
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<IngredientViewData>() {
        override fun areItemsTheSame(oldItem: IngredientViewData, newItem: IngredientViewData) =
            oldItem.recipeIngredient.id == newItem.recipeIngredient.id

        override fun areContentsTheSame(
            oldItem: IngredientViewData,
            newItem: IngredientViewData
        ) =
            oldItem.recipeIngredient == newItem.recipeIngredient
    }

    class IngredientItemListener(val clickListener: (IngredientViewData: IngredientViewData) -> Unit) {
        fun onClick(IngredientViewData: IngredientViewData) = clickListener(IngredientViewData)
    }
}



