package com.guidoroos.recepten.recipe


import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.guidoroos.recepten.db.Recipe

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
