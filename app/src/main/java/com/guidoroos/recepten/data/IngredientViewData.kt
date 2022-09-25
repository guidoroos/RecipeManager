package com.guidoroos.recepten.data

import com.guidoroos.recepten.db.Ingredient
import com.guidoroos.recepten.db.RecipeIngredient

data class IngredientViewData(
    val recipeIngredient: RecipeIngredient,
    val ingredient: Ingredient,
    val unit: UnitData?
)
