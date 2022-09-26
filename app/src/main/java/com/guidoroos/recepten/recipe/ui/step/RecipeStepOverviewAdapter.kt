package com.guidoroos.recepten.recipe.ui.step

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.guidoroos.recepten.databinding.RecipeStepItemBinding
import com.guidoroos.recepten.db.RecipeStep


class RecipeStepOverviewAdapter(private val listener: RecipeItemListener) :
    ListAdapter<RecipeStep, RecipeStepOverviewAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, listener)
    }

    class ViewHolder(private val binding: RecipeStepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecipeStep, listener: RecipeItemListener) {
            binding.listener = listener
            binding.data = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {

                val binding =
                    RecipeStepItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return ViewHolder(binding)
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<RecipeStep>() {
        override fun areItemsTheSame(oldItem: RecipeStep, newItem: RecipeStep) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RecipeStep,
            newItem: RecipeStep
        ) =
            oldItem == newItem
    }

    class RecipeItemListener(val clickListener: (RecipeStep: RecipeStep) -> Unit) {
        fun onClick(RecipeStep: RecipeStep) = clickListener(RecipeStep)
    }
}



