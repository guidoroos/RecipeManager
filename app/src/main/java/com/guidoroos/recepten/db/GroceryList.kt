package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_list")
data class GroceryList  (
    @PrimaryKey(autoGenerate = true)
    val recipeId : Long = 0,

)