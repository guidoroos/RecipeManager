package com.guidoroos.recepten.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.guidoroos.recepten.databinding.RecipeOverviewFragmentBinding
import com.guidoroos.recepten.main.viewmodel.RecipeOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeOverviewFragment : Fragment() {

    private val viewModel: RecipeOverviewViewModel by viewModels()
    private lateinit var binding:RecipeOverviewFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RecipeOverviewFragmentBinding.inflate(layoutInflater, container, false)

        val adapter = RecipeOverViewAdapter()
        binding.recipeListRecyclerview.adapter = adapter

        viewModel.recipeList.observe(viewLifecycleOwner, Observer {  it?.let {
            adapter.submitList(it)
        }
        })


        return binding.root
    }


}