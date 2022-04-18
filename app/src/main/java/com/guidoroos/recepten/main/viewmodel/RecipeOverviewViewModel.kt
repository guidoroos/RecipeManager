package com.guidoroos.recepten.main.viewmodel

import androidx.lifecycle.*
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.di.Filter
import com.guidoroos.recepten.di.FilterType
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeOverviewViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {


    private var filterSet = Filter(FilterType.NONE, 0L)


    val recipeList: LiveData<List<Recipe>> = repository.getFilteredRecipes(filterSet).asLiveData()


    fun clearFilter () {
        filterSet = Filter (FilterType.NONE,0L)
    }

    private fun setFilter (filterType:FilterType, value:Long) {
        filterSet = Filter (filterType, value)
        recipeList.switchMap {  }
    }

    fun setFilter (filterType:FilterType, value:String) {
        when (filterType) {
            FilterType.RECIPE_TYPE -> setTypeIdFilter(value)
            FilterType.CUISINE -> setCuisineIdFilter(value)
            else ->  clearFilter()
        }
    }

    private fun setTypeIdFilter(title: String) = CoroutineScope(Dispatchers.IO).launch {
        val typeId = repository.getTypeId(title)
        setFilter (FilterType.RECIPE_TYPE, typeId)
    }

    private fun setCuisineIdFilter (title:String) = CoroutineScope(Dispatchers.IO).launch {
        val cuisineId = repository.getCuisineId(title)
        setFilter (FilterType.CUISINE, cuisineId)
    }
}



