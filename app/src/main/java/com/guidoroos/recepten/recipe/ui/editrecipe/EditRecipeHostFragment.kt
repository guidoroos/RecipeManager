package com.guidoroos.recepten.recipe.ui.editrecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.RecipeHostFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditRecipeHostFragment : Fragment() {

    private lateinit var binding: RecipeHostFragmentBinding
    private val args:EditRecipeHostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeHostFragmentBinding.inflate(layoutInflater, container, false)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)


        binding.viewPager.adapter = EditRecipeFragmentStateAdapter(this, args.recipe)

        binding.tabs.tabGravity = TabLayout.GRAVITY_FILL

        // Bind tabs and viewpager
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            val resource = when (position) {
                0 -> R.string.recipe_overview
                1 -> R.string.recipe_ingredients
                2 -> R.string.recipe_steps
                else -> null
            }
            resource?.let {
                tab.text = getString(it)
            }

        }.attach()


        return binding.root
    }



}