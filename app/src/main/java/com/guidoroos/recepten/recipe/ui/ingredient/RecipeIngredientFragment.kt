package com.guidoroos.recepten.recipe.ui.ingredient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.guidoroos.recepten.R
import com.guidoroos.recepten.data.UnitEnum
import com.guidoroos.recepten.data.unitData
import com.guidoroos.recepten.databinding.IngredientsFragmentBinding
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.main.ui.RecipeOverViewAdapter
import com.guidoroos.recepten.main.ui.RecipeOverviewFragmentDirections
import com.guidoroos.recepten.recipe.ui.viewrecipe.RecipeFragment
import com.guidoroos.recepten.recipe.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class RecipeIngredientFragment : Fragment() {

    private val viewModel: RecipeViewModel by activityViewModels()
    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = IngredientsFragmentBinding.inflate(layoutInflater, container, false)

        recipe = arguments?.getParcelable("recipe")

        viewModel.getRecipeIngredients(recipe)


        val adapter = IngredientViewDataAdapter (IngredientViewDataAdapter.IngredientItemListener {
            //no implementation yet
        })

        binding.ingredientsRecyclerview.adapter = adapter

        viewModel.recipeIngredients.observe(viewLifecycleOwner
        ) { list ->
            adapter.submitList(list)
        }

        return binding.root
    }

    companion object {
        fun createInstance (recipe: Recipe?): RecipeIngredientFragment {
            val bundle = bundleOf("recipe" to recipe)
            return RecipeIngredientFragment().apply { arguments = bundle }
        }
    }



}