package com.guidoroos.recepten.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT id FROM recipe_type WHERE name = :title")
    suspend fun getRecipeTypeId (title:String) :Long

    @Query("SELECT id FROM cuisine WHERE name = :title")
    suspend fun getCuisineId (title:String) :Long

    @Query("SELECT name FROM cuisine WHERE id = :id")
    suspend fun getCuisineName(id: Long): String

    @Query("SELECT name FROM recipe_type WHERE id = :id")
    suspend fun getRecipeTypeName(id: Long): String

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