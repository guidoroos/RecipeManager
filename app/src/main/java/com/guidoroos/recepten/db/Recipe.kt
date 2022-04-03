package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val title: String,
    val description: String,
    val cuisineId : Long,
    val recipeTypeId : Long
)

