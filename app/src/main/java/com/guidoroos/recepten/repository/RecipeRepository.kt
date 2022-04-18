package com.guidoroos.recepten.repository

import androidx.lifecycle.LiveData
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.db.RecipeDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RecipeRepository @Inject constructor(dao: RecipeDao) {

    val allRecipes:Flow<List<Recipe>> = dao.getAllRecipes()
}