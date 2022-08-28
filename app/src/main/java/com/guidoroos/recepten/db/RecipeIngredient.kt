package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_ingredient")
data class RecipeIngredient  (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val recipeId : Long,
    val ingredientId:Long,
    val position:Int
)