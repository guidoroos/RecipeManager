package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_type")
data class ItemType  (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val name : String,
    val resourceId: Long
)