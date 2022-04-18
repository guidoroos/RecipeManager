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
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.main.model.SortingType
import com.guidoroos.recepten.main.viewmodel.RecipeOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeOverviewFragment : Fragment() {

    private val viewModel: RecipeOverviewViewModel by viewModels()
    private lateinit var binding:RecipeOverviewFragmentBinding

    private var sortingType = SortingType.NAME_ASC

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = RecipeOverviewFragmentBinding.inflate(layoutInflater, container, false)

        val adapter = RecipeOverViewAdapter()
        binding.recipeListRecyclerview.adapter = adapter

        viewModel.recipeList.observe(viewLifecycleOwner, Observer { list ->
            val sortedList = getSortedList(list)
            sortedList?.let {adapter.submitList(it)}
        }
        )

        val spinnerAdapter = requireContext().let {
            ArrayAdapter.createFromResource(
                it,
                R.array.sorting_values,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        }

        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent != null) {
                    val itemSelected =  parent.getItemAtPosition(position).toString()
                    sortingType = when (itemSelected) {
                            getString(R.string.alphabetical) -> SortingType.NAME_ASC
                        getString(R.string.alphabetical_reverse) -> SortingType.NAME_DESC
                            getString(R.string.time_created) -> SortingType.CREATED_ASC
                            getString(R.string.time_created_reverse) -> SortingType.CREATED_DESC
                            getString(R.string.time_last_seen) -> SortingType.LAST_SEEN_ASC
                            getString(R.string.time_last_seen_reverse) -> SortingType.LAST_SEEN_DESC
                        else -> SortingType.NAME_ASC
                    }

                    val list = viewModel.recipeList.value

                    val sortedList = getSortedList(list)
                    sortedList?.let {adapter.submitList(it)}

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            //empty method
            }
        }

        binding.spinnerSort.adapter = spinnerAdapter




        return binding.root
    }

    private fun getSortedList(list: List<Recipe>?) =
        when (sortingType) {
            SortingType.NAME_ASC -> list?.sortedBy { it.title }
            SortingType.NAME_DESC -> list?.sortedByDescending { it.title }
            SortingType.CREATED_ASC -> list?.sortedBy { it.timeCreated }
            SortingType.CREATED_DESC -> list?.sortedByDescending { it.timeCreated }
            SortingType.LAST_SEEN_ASC -> list?.sortedBy { it.timeLastSeen }
            SortingType.LAST_SEEN_DESC -> list?.sortedByDescending { it.timeLastSeen }
        }


}