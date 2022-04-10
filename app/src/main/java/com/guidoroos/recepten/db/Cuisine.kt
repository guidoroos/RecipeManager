package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cuisine")
data class Cuisine  (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String,
    val imageResourceId :Int? = null
)