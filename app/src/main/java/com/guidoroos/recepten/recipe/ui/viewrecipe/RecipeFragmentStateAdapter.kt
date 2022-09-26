package com.guidoroos.recepten.recipe.ui.viewrecipe


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.recipe.ui.ingredient.RecipeIngredientFragment
import com.guidoroos.recepten.recipe.ui.step.RecipeStepFragment
import com.guidoroos.recepten.recipe.ui.step.RecipeStepOverviewFragment

class RecipeFragmentStateAdapter(fragment: Fragment, private val recipe: Recipe?) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return  when (position) {
            0 -> RecipeFragment.createInstance(recipe)
            1 -> RecipeIngredientFragment.createInstance(recipe)
            2 -> RecipeStepOverviewFragment.createInstance(recipe)
            else -> throw IllegalArgumentException()
        }
    }
}
