package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unit_type")
data class UnitType  (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String,
    val standardUnitId: Long
)