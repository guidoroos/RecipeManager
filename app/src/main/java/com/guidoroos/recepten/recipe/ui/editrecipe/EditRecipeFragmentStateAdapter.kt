package com.guidoroos.recepten.recipe.ui.editrecipe


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.recipe.ui.ingredient.RecipeIngredientFragment
import com.guidoroos.recepten.recipe.ui.step.RecipeStepFragment

class EditRecipeFragmentStateAdapter(fragment: Fragment, private val recipe: Recipe?) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> EditRecipeFragment.createInstance(recipe)
            1 -> RecipeIngredientFragment()
            2 -> RecipeStepFragment()
            else -> throw IllegalArgumentException()
        }
    }

}
