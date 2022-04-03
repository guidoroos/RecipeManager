package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step")
data class Step(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val description: String,
    val position: Int,
    val timer: Long?
)