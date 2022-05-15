package com.guidoroos.recepten.filter

import android.app.Dialog
import android.content.res.Resources
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.guidoroos.recepten.R
import com.guidoroos.recepten.databinding.RecipeFilterFragmentBinding
import com.guidoroos.recepten.filter.model.Filter
import com.guidoroos.recepten.filter.model.FilterItem
import com.guidoroos.recepten.filter.model.FilterTimeUnit
import com.guidoroos.recepten.filter.model.FilterType
import com.guidoroos.recepten.main.ui.RecipeOverviewFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFilterDialogFragment: DialogFragment() {

    private val viewModel: RecipeFilterViewModel by viewModels()
    private lateinit var binding:RecipeFilterFragmentBinding
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
        detailAdapter = RecipeFilterDetailAdapter(RecipeFilterDetailAdapter.FilterDetailItemListener{filterItem ->
            val filter = Filter(this.filterType,filterItem.filterItemValue)
            val parentFragment = parentFragment as? RecipeOverviewFragment
            if (parentFragment == null) {
                Toast.makeText(requireContext(),getString(R.string.filter_error),Toast.LENGTH_LONG).show()
            } else {
                parentFragment.setFilter(filter)
            }
        })

        setFilterOverviewData()

        binding.filterToolbar.inflateMenu(R.menu.menu_filter)
        binding.filterToolbar.setOnMenuItemClickListener {
            dialog?.dismiss()
            true
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window

        // set "origin" to top left corner
        window?.setGravity(Gravity.TOP or Gravity.START)

        val params = window?.attributes

        val yPixels = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            120f,
            Resources.getSystem().displayMetrics
        )

        params?.y = yPixels.toInt()
        window?.attributes = params

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setNavIconVisibility(shouldShowIcon: Boolean) {
        if (shouldShowIcon) {
            binding.filterToolbar.navigationIcon =
                getDrawable(requireContext(), android.R.drawable.arrow_up_float)
            binding.filterToolbar.setNavigationOnClickListener {
                setFilterOverviewData()
            }
        } else binding.filterToolbar.navigationIcon = null
    }

    private fun  setFilterOverviewData() {
        filterType = FilterType.NONE
        removeObservers ()
        binding.filterRecyclerview.adapter = filterAdapter
        filterAdapter.submitList(viewModel.filterTypes)
    }

    private fun setFilterTypeDetailData (filterType: FilterType) {
        this.filterType = filterType
        removeObservers()
        binding.filterRecyclerview.adapter = detailAdapter

        when (filterType) {
            FilterType.NONE -> {
              //no op
            }
            FilterType.LAST_SEEN, FilterType.CREATED -> {
                val list = viewModel.timeFilterData.map { item ->
                    val name = getString(item.nameResourceId)
                    val time = getMilliSecondsFromTimeUnit(item)
                    FilterItem(name,time)
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

    private fun removeObservers () {
        viewModel.cuisineFilterData.removeObservers(this)
        viewModel.recipeTypeFilterData.removeObservers(this)
    }

    private fun getMilliSecondsFromTimeUnit (unit:FilterTimeUnit):Long {
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

