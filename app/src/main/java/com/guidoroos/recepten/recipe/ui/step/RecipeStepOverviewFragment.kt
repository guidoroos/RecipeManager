package com.guidoroos.recepten.recipe.ui.step

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.guidoroos.recepten.databinding.RecipeStepOverviewFragmentBinding
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.recipe.ui.ingredient.RecipeIngredientFragment
import com.guidoroos.recepten.recipe.viewmodel.RecipeViewModel

class RecipeStepOverviewFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels()
    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = RecipeStepOverviewFragmentBinding.inflate(layoutInflater, container, false)

        recipe = arguments?.getParcelable("recipe")

        viewModel.getRecipeSteps(recipe)


        val adapter = RecipeStepOverviewAdapter (RecipeStepOverviewAdapter.RecipeItemListener {
            //no implementation yet
        })

        binding.stepOverviewRecyclerview.adapter = adapter

        viewModel.recipeSteps.observe(viewLifecycleOwner
        ) { list ->
            adapter.submitList(list)
        }

        return binding.root
    }

    companion object {
        fun createInstance (recipe: Recipe?): RecipeStepOverviewFragment{
            val bundle = bundleOf("recipe" to recipe)
            return RecipeStepOverviewFragment().apply { arguments = bundle }
        }
    }

}