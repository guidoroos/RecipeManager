package com.guidoroos.recepten.data

import com.guidoroos.recepten.R


enum class IngredientType(val resourceId: Int) {
    Vegetables(
        R.string.vegetables
    ),
    MeatFish(
       R.string.meats_fish_substitutes
    ),
    Dairy(
       R.string.dairy
    ),
    Grains(
       R.string.grains_rice
    ),
    Spice(
       R.string.spices
    ),
    Fats(
       R.string.fats_oils
    ),
    NutsBaking(
       R.string.nuts_baking_products
    ),
    Beans(
       R.string.beans_legumes
    ),
    Fruits(
       R.string.fruits
    ),
    NonFood(
       R.string.non_food
    )
}