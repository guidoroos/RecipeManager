package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unit")
data class Unit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val description: String?,
    val perStandardUnit: Float,
    val unitTypeId : Long
)