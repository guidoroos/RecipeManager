package com.guidoroos.recepten.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.RecipeOverviewFragmentBinding
import com.guidoroos.recepten.di.FilterType
import com.guidoroos.recepten.main.model.SortingType
import com.guidoroos.recepten.main.viewmodel.RecipeOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class RecipeOverviewFragment : Fragment() {

    private val viewModel: RecipeOverviewViewModel by viewModels()
    private lateinit var binding: RecipeOverviewFragmentBinding

    private var sortingType = SortingType.NAME_ASC
    private var clicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RecipeOverviewFragmentBinding.inflate(layoutInflater, container, false)

        val adapter = RecipeOverViewAdapter()
        binding.recipeListRecyclerview.adapter = adapter

        viewModel.filteredSortedList.observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
        }
        )

        binding.iconFilter.setOnClickListener {
            if (clicked) {
                viewModel.clearFilter()
            } else {
                viewModel.setFilter(FilterType.CUISINE, "Indian")
            }
            clicked = !clicked
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
                    val itemSelected = parent.getItemAtPosition(position).toString()
                    viewModel.sortingType.value = when (itemSelected) {
                        getString(R.string.alphabetical) -> SortingType.NAME_ASC
                        getString(R.string.alphabetical_reverse) -> SortingType.NAME_DESC
                        getString(R.string.time_created) -> SortingType.CREATED_ASC
                        getString(R.string.time_created_reverse) -> SortingType.CREATED_DESC
                        getString(R.string.time_last_seen) -> SortingType.LAST_SEEN_ASC
                        getString(R.string.time_last_seen_reverse) -> SortingType.LAST_SEEN_DESC
                        else -> SortingType.NAME_ASC
                    }
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