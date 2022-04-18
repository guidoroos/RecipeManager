package com.guidoroos.recepten.repository

import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.db.RecipeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepository @Inject constructor(private val dao: RecipeDao) {

    val allRecipes:Flow<List<Recipe>> = dao.getAllRecipes()

    fun getCuisineId (title:String) = dao.getCuisineId(title)

    fun getTypeId (title:String) = dao.getRecipeTypeId(title)
}


