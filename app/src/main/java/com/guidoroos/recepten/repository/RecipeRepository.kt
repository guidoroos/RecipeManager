package com.guidoroos.recepten.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.db.RecipeDao
import com.guidoroos.recepten.di.Filter
import com.guidoroos.recepten.di.FilterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RecipeRepository @Inject constructor(private val dao: RecipeDao) {

    fun getCuisineId (title:String)= dao.getCuisineId(title)

    fun getTypeId (title:String)= dao.getRecipeTypeId(title)

    fun getFilteredRecipes (filter: Filter):Flow<List<Recipe>> {
        return when (filter.filterType) {
            FilterType.NONE -> dao.getAllRecipes()
            FilterType.CUISINE -> dao.getRecipesFilteredByCuisine(filter.filterValue)
            FilterType.RECIPE_TYPE -> dao.getRecipesFilteredByRecipeType(filter.filterValue)
            FilterType.CREATED -> dao.getRecipesFilteredByTimeCreated(filter.filterValue)
            FilterType.LAST_SEEN -> dao.getRecipesFilteredByLastSeen(filter.filterValue)
        }
    }

    private operator fun Long.compareTo(filterValue: Any?): Int {
        return if (filterValue is Long) {
            (this - filterValue).toInt()
        }
        else -1
    }

}