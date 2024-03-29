package com.guidoroos.recepten.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.RecipeOverviewFragmentBinding
import com.guidoroos.recepten.main.model.SortingType
import com.guidoroos.recepten.main.viewmodel.RecipeOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeOverviewFragment : Fragment() {

    private val viewModel: RecipeOverviewViewModel by activityViewModels()
    private lateinit var binding: RecipeOverviewFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RecipeOverviewFragmentBinding.inflate(layoutInflater, container, false)

        binding.toolbar.title = getString(R.string.all_recipes)

        val adapter = RecipeOverViewAdapter(RecipeOverViewAdapter.RecipeOverviewItemListener { recipe ->
           findNavController().navigate(RecipeOverviewFragmentDirections
               .actionRecipeOverviewFragmentToRecipeHostFragment(recipe))
        })
        binding.recipeListRecyclerview.adapter = adapter

        if (viewModel.isFilterSet()) {
            binding.iconFilter.background = getDrawable(requireContext(),R.drawable.ic_clear_filter)
        }

        viewModel.filteredSortedList.observe(viewLifecycleOwner
        ) { list ->
            adapter.submitList(list)
        }

        binding.iconFilter.setOnClickListener {
            if (viewModel.isFilterSet()) {
                viewModel.clearFilter()
                binding.iconFilter.background = getDrawable(requireContext(),R.drawable.ic_filter)
            } else {
                findNavController().navigate(R.id.recipeFilterFragment)
            }
        }

        val spinnerAdapter = requireContext().let {
            ArrayAdapter.createFromResource(
                it,
                R.array.sorting_values,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent != null) {
                    val sortingType =  when (parent.getItemAtPosition(position).toString()) {
                        getString(R.string.alphabetical) -> SortingType.NAME_ASC
                        getString(R.string.alphabetical_reverse) -> SortingType.NAME_DESC
                        getString(R.string.time_created) -> SortingType.CREATED_ASC
                        getString(R.string.time_created_reverse) -> SortingType.CREATED_DESC
                        getString(R.string.time_last_seen) -> SortingType.LAST_SEEN_ASC
                        getString(R.string.time_last_seen_reverse) -> SortingType.LAST_SEEN_DESC
                        else -> SortingType.NAME_ASC
                    }
                    viewModel.setSortingType(sortingType)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //empty method
            }
        }
        binding.spinnerSort.adapter = spinnerAdapter

        return binding.root
    }


}