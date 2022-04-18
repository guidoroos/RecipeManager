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

    val recipeList: LiveData<List<Recipe>> = repository.allRecipes.asLiveData()

     private val _filterSet = MutableLiveData<Filter>()
         val filterSet: LiveData <Filter>
             get() = _filterSet


    fun clearFilter () {
       _filterSet.postValue (Filter (FilterType.NONE,0L))
    }

    private fun setFilter (filterType:FilterType, value:Long) {
       _filterSet.postValue ( Filter (filterType, value))
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



private operator fun Long.compareTo(filterValue: Any?): Int {
    return if (filterValue is Long) {
        (this - filterValue).toInt()
    }
    else -1
}
