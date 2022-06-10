package com.guidoroos.recepten.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe )

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