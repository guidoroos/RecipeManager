package com.guidoroos.recepten.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guidoroos.recepten.util.Fraction

@Entity(tableName = "recipe_ingredient")
data class RecipeIngredient  (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val recipeId : Long,
    val ingredientId: Long,
    val position:Int,
    val value: Int,
    val valueDenominator:Int = 1,
    val unit: String) {

    val fraction:Fraction
        get() = Fraction(value,valueDenominator)
    }



