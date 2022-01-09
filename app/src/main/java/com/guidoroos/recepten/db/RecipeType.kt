package com.guidoroos.recepten.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipe_type")
data class RecipeType  (
        @PrimaryKey(autoGenerate = true)
        val id : Long = 0,
        val name : String
)