package com.guidoroos.recepten.filter


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData

import com.guidoroos.recepten.filter.model.FilterTimeUnit
import com.guidoroos.recepten.filter.model.FilterType
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeFilterViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {

    val filterTypes = FilterType.values().toMutableList().apply {remove(FilterType.NONE)}

    val timeFilterData = FilterTimeUnit.values().toList()

    val cuisineFilterData = repository.getAllCuisines().asLiveData()
    val recipeTypeFilterData = repository.getAllRecipeTypes().asLiveData()

}