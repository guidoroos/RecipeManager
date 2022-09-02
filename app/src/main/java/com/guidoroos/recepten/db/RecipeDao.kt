package com.guidoroos.recepten.db

import androidx.room.*
import com.guidoroos.recepten.data.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe )

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe ORDER BY title")
    fun getAllRecipes(): Flow<List<Recipe>>

    //
    suspend fun createNewIngredient () {

    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: Ingredient)

    private suspend fun insertIngredient (name:String, type:IngredientType):Ingredient {
        val ingredient = Ingredient(name=name, typeRes = type.resourceId )
        insertIngredient(ingredient)
        return ingredient
    }

    suspend fun Recipe.addIngredient(
        name: String,
        type: IngredientType,
        position: Int,
        unit: UnitEnum? = null,
        value: Float
    ) {
        val ingredient = insertIngredient(name, type)

        insertRecipeIngredient(
            ingredient,
            this,
            position,
            unitData[unit],
            value
        )
    }

    @Update
    suspend fun updateIngredient(ingredient:Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient:Ingredient
    )

    @Query("SELECT id FROM ingredient WHERE name = :name")
    fun getIngredientId(name:String): Long?
    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(recipeIngredient: RecipeIngredient)

    @Update
    suspend fun updateRecipeIngredient(recipeIngredient:RecipeIngredient)

    @Delete
    suspend fun deleteRecipeIngredient(recipeIngredient:RecipeIngredient
    )



    private suspend fun insertRecipeIngredient (
        ingredient:Ingredient,
        recipe:Recipe,
        position:Int,
        unit:UnitData? = null,
        value:Float,
    ) {
        val recipeIngredient = RecipeIngredient (
        recipeId = recipe.id,
        ingredientId = ingredient.id,
        position = position,
        value = value,
        unitRes= unit?.nameResource
        )

        insertRecipeIngredient (recipeIngredient)
    }

    //

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: RecipeStep )

    @Update
    suspend fun updateStep(step: RecipeStep)

    @Delete
    suspend fun deleteStep(step: RecipeStep)

    suspend fun Recipe.addStep (
        description: String,
        position: Int,
        timerSeconds: Int? = null
    ) {
        val step = RecipeStep (
            recipeId = this.id,
            description = description,
            position = position,
            timerSeconds = timerSeconds
        )
        insertStep (step)
    }












//
//    @Update
//    suspend fun update(: )
//
//    @Delete
//    suspend fun delete(: )
//
//    @Query("SELECT * FROM ")
//    fun getAll(): List<>

}