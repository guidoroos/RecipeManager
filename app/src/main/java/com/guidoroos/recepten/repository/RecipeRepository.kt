package com.guidoroos.recepten.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.guidoroos.recepten.data.*
import com.guidoroos.recepten.db.Ingredient
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.db.RecipeDao
import com.guidoroos.recepten.db.RecipeStep
import com.guidoroos.recepten.filter.model.FilterItem
import com.guidoroos.recepten.filter.model.FilterValueString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class RecipeRepository @Inject constructor(private val dao: RecipeDao) {

    val allRecipes:Flow<List<Recipe>> = dao.getAllRecipes()

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

    fun getRecipeIngredients(recipe: Recipe): List<IngredientViewData> {
        val recipeIngredients = dao.getIngredientsForRecipe(recipe.id)

        return recipeIngredients.map { recipeIngredient ->
                IngredientViewData(
                    recipeIngredient,
                    dao.getIngredientFromId(recipeIngredient.ingredientId),
                    getUnitData(recipeIngredient.unit)
                )
        }
    }


    fun getUnitData (unitString:String):UnitData? {
        return  unitData [UnitEnum.valueOf(unitString)]
    }


    suspend fun storeRecipe (recipe:Recipe) {
        dao.insertRecipe(recipe)
    }

    suspend fun updateRecipe (recipe:Recipe) {
        dao.updateRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe:Recipe) {
        dao.deleteRecipe(recipe)
    }

    fun getRecipeSteps(recipe: Recipe): List<RecipeStep> {
        return  dao.getStepsForRecipe(recipe.id)
    }


}


