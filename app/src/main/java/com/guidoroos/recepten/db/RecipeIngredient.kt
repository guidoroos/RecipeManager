package com.guidoroos.recepten.db

import androidx.room.Entity

@Entity(tableName = "recipe_ingredient")
data class RecipeIngredient  (
    val recipeId : Long,
    val ingredientId:Int
)