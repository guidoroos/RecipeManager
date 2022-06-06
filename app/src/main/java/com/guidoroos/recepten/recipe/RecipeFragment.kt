package com.guidoroos.recepten.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.guidoroos.recepten.databinding.RecipeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var binding: RecipeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeFragmentBinding.inflate(layoutInflater, container, false)
        arguments?.let { bundle ->
            val args = RecipeFragmentArgs.fromBundle(bundle)
            binding.recipe = args.recipe
            lifecycleScope.launch {
                val recipeTypeName = viewModel.getRecipeTypeName(args.recipe.recipeTypeId)
                binding.recipeTypeTextView.text = recipeTypeName

                args.recipe.cuisineId?.let { id ->
                    val cuisineName = viewModel.getCuisineName(id)
                    binding.countryTextView.text = cuisineName
                }
            }
        }
        return binding.root
    }
}