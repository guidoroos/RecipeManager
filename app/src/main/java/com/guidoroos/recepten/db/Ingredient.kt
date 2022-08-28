package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val value: Int,
    val unitId : Long,
    val itemId : Long
)

