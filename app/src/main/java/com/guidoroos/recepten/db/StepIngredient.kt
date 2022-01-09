package com.guidoroos.recepten.db

import androidx.room.Entity

@Entity(tableName = "step_ingredient")
data class StepIngredient  (
    val stepId : Long,
    val ingredientId:Int
)