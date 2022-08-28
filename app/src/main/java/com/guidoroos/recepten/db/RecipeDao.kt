package com.guidoroos.recepten.db

import androidx.room.*
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient)

    @Update
    suspend fun updateIngredient(ingredient:Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient:Ingredient
    )

    //

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(recipeIngredient: RecipeIngredient)

    @Update
    suspend fun updateRecipeIngredient(recipeIngredient:RecipeIngredient)

    @Delete
    suspend fun deleteRecipeIngredient(recipeIngredient:RecipeIngredient
    )

    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(recipeIngredient: RecipeIngredient)

    @Update
    suspend fun updateRecipeIngredient(recipeIngredient:RecipeIngredient)

    @Delete
    suspend fun deleteRecipeIngredient(recipeIngredient:RecipeIngredient
    )

    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllItemTypes(vararg itemType: ItemType)







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