package com.guidoroos.recepten.main.viewmodel

import androidx.lifecycle.*
import com.guidoroos.recepten.filter.model.Filter
import com.guidoroos.recepten.filter.model.FilterType
import com.guidoroos.recepten.main.model.SortingType
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeOverviewViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {

    private var filterSet: MutableStateFlow<Filter> = MutableStateFlow(Filter(FilterType.NONE, 0L))
    private var sortingType: MutableStateFlow<SortingType> = MutableStateFlow(SortingType.NAME_ASC)


    @ExperimentalCoroutinesApi
    val filteredList = filterSet.flatMapLatest { filter ->
        val list = repository.allRecipes

        return@flatMapLatest when (filter.filterType) {
            FilterType.NONE -> list
            FilterType.CUISINE -> list.map { it.filter { item -> item.cuisineId == filterSet.value.filterValue } }
            FilterType.CREATED -> list.map { it.filter { item -> item.timeCreated < filterSet.value.filterValue } }
            FilterType.LAST_SEEN -> list.map { it.filter { item -> item.timeLastSeen < filterSet.value.filterValue } }
            FilterType.RECIPE_TYPE -> list.map {it.filter { item -> item.recipeTypeId == filterSet.value.filterValue }}
        }
    }

    @ExperimentalCoroutinesApi
    val filteredSortedList = sortingType.flatMapLatest { sortingType ->
        return@flatMapLatest when (sortingType) {
            SortingType.NAME_ASC -> filteredList.map { list -> list.sortedBy { it.title } }
            SortingType.NAME_DESC -> filteredList.map { list -> list.sortedByDescending { it.title } }
            SortingType.CREATED_ASC -> filteredList.map { list -> list.sortedBy { it.timeCreated } }
            SortingType.CREATED_DESC -> filteredList.map { list -> list.sortedByDescending { it.timeCreated } }
            SortingType.LAST_SEEN_ASC -> filteredList.map { list -> list.sortedBy { it.timeLastSeen } }
            SortingType.LAST_SEEN_DESC -> filteredList.map { list -> list.sortedByDescending { it.timeLastSeen } }
        }
    }.asLiveData()


    fun setSortingType (type: SortingType) {
        sortingType.value = type
    }

    fun clearFilter() {
        filterSet.value = Filter(FilterType.NONE, 0L)
    }

    private fun setFilter(filterType: FilterType, value: Long) {
        filterSet.value = Filter(filterType, value)
    }

    fun setFilter(filter:Filter) {
        filterSet.value = filter
    }

    fun setFilter(filterType: FilterType, value: String) {
        when (filterType) {
            FilterType.RECIPE_TYPE -> setTypeIdFilter(value)
            FilterType.CUISINE -> setCuisineIdFilter(value)
            else -> clearFilter()
        }
    }

    private fun setTypeIdFilter(title: String) = CoroutineScope(Dispatchers.IO).launch {
        val typeId = repository.getTypeId(title)
        setFilter(FilterType.RECIPE_TYPE, typeId)
    }

    private fun setCuisineIdFilter(title: String) = CoroutineScope(Dispatchers.IO).launch {
        val cuisineId = repository.getCuisineId(title)
        setFilter(FilterType.CUISINE, cuisineId)
    }
}


private operator fun Long.compareTo(filterValue: Any?): Int {
    return if (filterValue is Long) {
        (this - filterValue).toInt()
    } else -1
}
