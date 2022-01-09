package com.guidoroos.recepten.db

import androidx.room.Entity

@Entity(tableName = "recipe_step")
data class RecipeStep  (
    val recipeId : Long,
    val stepId:Int
)


