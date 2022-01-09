package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "operation_type")
data class OperationType  (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val resourceId : Long,
    val operationName:String
)