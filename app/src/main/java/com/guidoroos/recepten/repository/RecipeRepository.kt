package com.guidoroos.recepten.repository

import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.db.RecipeDao
import com.guidoroos.recepten.filter.model.FilterItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class RecipeRepository @Inject constructor(private val dao: RecipeDao) {

    val allRecipes:Flow<List<Recipe>> = dao.getAllRecipes()

    fun getCuisineId (title:String) = dao.getCuisineId(title)
    fun getTypeId (title:String) = dao.getRecipeTypeId(title)

    private fun getCuisineName (id:Long) = dao.getCuisineName(id)
    private fun getRecipeTypeName (id:Long) = dao.getRecipeTypeName(id)

    fun getAllRecipeTypes(): Flow<List<FilterItem>> {
        return allRecipes.map { list ->
            list.map { recipe ->
                val id = recipe.recipeTypeId
                val name = getRecipeTypeName(id)
                FilterItem(name,id)}
                .distinct()
        }
    }

    fun getAllCuisines(): Flow<List<FilterItem>> {
        return allRecipes.map { recipe ->
            recipe.filter {
                it.cuisineId != null
            }.mapNotNull { item ->
                val id = item.cuisineId
                val name = id?.let { getCuisineName(it) }
                if (name != null) {
                    FilterItem(name, id)
                } else null
            }
                .distinct()
        }
    }
}


