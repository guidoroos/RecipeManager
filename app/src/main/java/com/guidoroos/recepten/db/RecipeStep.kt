package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step")
data class RecipeStep(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val recipeId:Long,
    val description: String,
    val position: Int,
    val timerSeconds: Int? = null
)