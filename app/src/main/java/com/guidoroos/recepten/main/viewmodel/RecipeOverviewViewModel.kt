package com.guidoroos.recepten.main.viewmodel

import androidx.lifecycle.*
import com.guidoroos.recepten.filter.model.*
import com.guidoroos.recepten.main.model.SortingType
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RecipeOverviewViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {

    private var filterSet: MutableStateFlow<Filter> = MutableStateFlow(Filter(FilterType.NONE, null))
    private var sortingType: MutableStateFlow<SortingType> = MutableStateFlow(SortingType.NAME_ASC)

    val filterTypes = FilterType.values().toMutableList().apply {remove(FilterType.NONE)}
    val timeFilterData = FilterTimeUnit.values().toList()
    val cuisineFilterData = repository.getAllCuisines().asLiveData()
    val recipeTypeFilterData = repository.getAllRecipeTypes().asLiveData()


    @ExperimentalCoroutinesApi
    val filteredList = filterSet.flatMapLatest { filter ->
        val list = repository.allRecipes

        return@flatMapLatest when (filter.filterType) {
            FilterType.NONE -> list
            FilterType.CUISINE -> list.map { it.filter {
                    item -> item.cuisine == (filterSet.value.filterValue as FilterValueString).value }}
            FilterType.CREATED -> list.map { it.filter {
                    item -> item.timeCreated > (filterSet.value.filterValue as FilterValueLong).value } }
            FilterType.LAST_SEEN -> list.map { it.filter {
                    item -> item.timeLastSeen > (filterSet.value.filterValue as FilterValueLong).value } }
            FilterType.RECIPE_TYPE -> list.map {it.filter {
                    item -> item.recipeType == (filterSet.value.filterValue as FilterValueString).value }}
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
        filterSet.value = Filter(FilterType.NONE, null)
    }

    fun setFilter(filter:Filter) {
        filterSet.value = filter
    }

    fun isFilterSet() : Boolean {
        return filterSet.value.filterType != FilterType.NONE
    }

}



