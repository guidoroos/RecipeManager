package com.guidoroos.recepten.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT id FROM recipe_type WHERE name = :title")
    fun getRecipeTypeId (title:String) :Long

    @Query("SELECT id FROM cuisine WHERE name = :title")
    fun getCuisineId (title:String) :Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cuisine: Cuisine)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipeType: RecipeType )

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg s: RecipeType )

    @Query("SELECT * FROM recipe ORDER BY title")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE recipeTypeId =:recipeTypeId ORDER BY title")
    fun getRecipesFilteredByRecipeType(recipeTypeId:Long): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE cuisineId =:cuisineId ORDER BY title")
    fun getRecipesFilteredByCuisine(cuisineId:Long): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE timeLastSeen > :timeStamp ORDER BY title")
    fun getRecipesFilteredByLastSeen(timeStamp:Long): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE timeCreated > :timeStamp ORDER BY title")
    fun getRecipesFilteredByTimeCreated(timeStamp:Long): Flow<List<Recipe>>


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