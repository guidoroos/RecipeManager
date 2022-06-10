package com.guidoroos.recepten.filter.ui

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.RecipeFilterFragmentBinding
import com.guidoroos.recepten.filter.model.*
import com.guidoroos.recepten.main.viewmodel.RecipeOverviewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFilterFragment : Fragment() {

    private val viewModel: RecipeOverviewViewModel by activityViewModels()
    private lateinit var binding: RecipeFilterFragmentBinding
    private lateinit var filterAdapter: RecipeFilterAdapter
    private lateinit var detailAdapter: RecipeFilterDetailAdapter
    private var filterType = FilterType.NONE

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecipeFilterFragmentBinding.inflate(layoutInflater, container, false)

        filterAdapter = RecipeFilterAdapter(RecipeFilterAdapter.FilterItemListener { filterType ->
            setFilterTypeDetailData(filterType)
        })
        detailAdapter =
            RecipeFilterDetailAdapter(RecipeFilterDetailAdapter.FilterDetailItemListener { filterItem ->
                val filter = Filter(this.filterType, filterItem.filterItemValue)
                viewModel.setFilter(filter)
                findNavController().navigate(R.id.action_recipeFilterFragment_to_recipeOverviewFragment)

            }
            )

        setFilterOverviewData()

        binding.filterToolbar.title = getString(R.string.choose_filter)
        binding.filterToolbar.inflateMenu(R.menu.menu_filter)
        binding.filterToolbar.setOnMenuItemClickListener {
            this.parentFragmentManager.popBackStack()
            true
        }

        return binding.root
    }

    private fun setNavIconVisibility(shouldShowIcon: Boolean) {
        if (shouldShowIcon) {
            binding.filterToolbar.navigationIcon =
                getDrawable(requireContext(), R.drawable.ic_arrow_back)
            binding.filterToolbar.setNavigationOnClickListener {
                setFilterOverviewData()
            }
        } else binding.filterToolbar.navigationIcon = null
    }

    private fun setFilterOverviewData() {
        setNavIconVisibility(false)
        filterType = FilterType.NONE
        removeObservers()
        binding.filterRecyclerview.adapter = filterAdapter
        filterAdapter.submitList(viewModel.filterTypes)
    }

    private fun setFilterTypeDetailData(filterType: FilterType) {
        this.filterType = filterType
        removeObservers()
        setNavIconVisibility(true)
        binding.filterRecyclerview.adapter = detailAdapter

        when (filterType) {
            FilterType.NONE -> {
                //no op
            }
            FilterType.LAST_SEEN, FilterType.CREATED -> {
                val list = viewModel.timeFilterData.map { item ->
                    val name = getString(item.nameResourceId)
                    val time = getMilliSecondsFromTimeUnit(item)
                    FilterItem(name, FilterValueLong(time))
                }
                detailAdapter.submitList(list)
            }
            FilterType.RECIPE_TYPE -> {
                viewModel.recipeTypeFilterData.observe(viewLifecycleOwner) { list ->
                    detailAdapter.submitList(list)
                }
            }
            FilterType.CUISINE -> {
                viewModel.cuisineFilterData.observe(viewLifecycleOwner) { list ->
                    detailAdapter.submitList(list)
                }
            }
        }
    }

    private fun removeObservers() {
        viewModel.cuisineFilterData.removeObservers(this)
        viewModel.recipeTypeFilterData.removeObservers(this)
    }

    private fun getMilliSecondsFromTimeUnit(unit: FilterTimeUnit): Long {
        val c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        when (unit) {
            FilterTimeUnit.LAST_WEEK -> c.add(Calendar.WEEK_OF_YEAR, -1)
            FilterTimeUnit.LAST_MONTH -> c.add(Calendar.MONTH, -1)
            FilterTimeUnit.LAST_QUARTER -> c.add(Calendar.MONTH, -3)
            FilterTimeUnit.LAST_YEAR -> c.add(Calendar.YEAR, -1)
        }
        return c.timeInMillis
    }
}

