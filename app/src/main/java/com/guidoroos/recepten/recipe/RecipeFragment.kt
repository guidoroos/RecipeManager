package com.guidoroos.recepten.recipe

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.guidoroos.recepten.MainActivity
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.EditRecipeFragmentBinding.inflate
import com.guidoroos.recepten.databinding.RecipeFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {


    private val viewModel: RecipeViewModel by viewModels()
    private lateinit var binding: RecipeFragmentBinding
    private val args:RecipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeFragmentBinding.inflate(layoutInflater, container, false)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.recipe = args.recipe

        binding.toolbar.inflateMenu(R.menu.menu_modify)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.modify_icon -> {
                    findNavController().navigate(RecipeFragmentDirections.actionRecipeFragmentToEditRecipeFragment(args.recipe))
                    true
                }
                else -> {
                    true
                }
            }
        }
        return binding.root
    }


    private fun navigateToEditRecipeFragment() {

    }




}