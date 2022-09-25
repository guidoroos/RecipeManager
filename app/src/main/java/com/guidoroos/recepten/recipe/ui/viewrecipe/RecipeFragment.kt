package com.guidoroos.recepten.recipe.ui.viewrecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.RecipeFragmentBinding
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.recipe.viewmodel.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {


    private val viewModel: RecipeViewModel by activityViewModels()
    private lateinit var binding: RecipeFragmentBinding
    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeFragmentBinding.inflate(layoutInflater, container, false)

        recipe = arguments?.getParcelable("recipe")

        binding.recipe = recipe

        val toolbar = requireParentFragment().view?.findViewById<MaterialToolbar>(R.id.toolbar)

        toolbar?.apply {
            title = recipe?.title
            inflateMenu(R.menu.menu_modify)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.modify_icon -> {
                        findNavController().navigate(
                            RecipeHostFragmentDirections.actionRecipeHostFragmentToEditRecipeHostFragment(recipe)
                        )
                        true
                    }
                    R.id.delete_icon -> {
                        recipe?.let { viewModel.deleteRecipe(it)}

                        findNavController().popBackStack(R.id.recipeOverviewFragment, false)
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
        return binding.root
    }

    companion object {
        fun createInstance (recipe:Recipe?): RecipeFragment {
            val bundle = bundleOf("recipe" to recipe)
            return RecipeFragment().apply { arguments = bundle }
        }
    }


}