package com.guidoroos.recepten.repository

import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.db.RecipeDao
import com.guidoroos.recepten.filter.model.FilterItem
import com.guidoroos.recepten.filter.model.FilterValueString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class RecipeRepository @Inject constructor(private val dao: RecipeDao) {

    val allRecipes:Flow<List<Recipe>> = dao.getAllRecipes()

    suspend fun storeRecipe (recipe:Recipe) {
        dao.insert(recipe)
    }

    fun getAllRecipeTypes(): Flow<List<FilterItem>> {
        return allRecipes.map { list ->
        list.distinctBy { it.recipeType }
            .map {FilterItem(it.recipeType,FilterValueString(it.recipeType))}
        }
    }

    fun getAllCuisines(): Flow<List<FilterItem>> {
        return allRecipes.map { list ->
            list.distinctBy { it.cuisine }
                .map {FilterItem(it.cuisine,FilterValueString(it.cuisine))}
        }
    }


}


