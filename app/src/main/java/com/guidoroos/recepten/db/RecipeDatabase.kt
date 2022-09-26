package com.guidoroos.recepten.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.guidoroos.recepten.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        GroceryList::class,
        Ingredient::class,
        Recipe::class,
        RecipeIngredient::class,
        RecipeStep::class],
    version = 1, exportSchema = false
)

abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): RecipeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipe_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(RecipeDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private class RecipeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    database.recipeDao.apply {

                      val pastaRecipeId = insertRecipe(Recipe(
                            title = "Pasta Bolognese",
                            imageResourceUri = "example_pasta",
                            description = "Italian Pasta",
                            cuisine = "Italian",
                            recipeType = "Dinner",
                            numberPortions = 4,
                            level = 1,
                            minutesDuration = 45
                        ))

                        val pastaRecipe = getRecipeById (pastaRecipeId)

                        pastaRecipe.addIngredient(
                            name = "olive oil",
                            type = IngredientType.Fats,
                            position = 0,
                            unit = UnitEnum.TableSpoon,
                            value = 2,
                        )
                        pastaRecipe.addIngredient(
                            name = "minced beef",
                            type = IngredientType.MeatFish,
                            position = 1,
                            unit = UnitEnum.Gram,
                            value = 800,
                        )
                        pastaRecipe.addIngredient(
                            name = "onion",
                            type = IngredientType.Vegetables,
                            position = 2,
                            value = 1,
                            valueDenominator = 2
                        )
                        pastaRecipe.addIngredient(
                            name = "garlic cloves",
                            type = IngredientType.Vegetables,
                            position = 3,
                            value = 2,
                        )
                        pastaRecipe.addIngredient(
                            name = "stock cube",
                            type = IngredientType.Spice,
                            position = 4,
                            value = 1,
                        )
                        pastaRecipe.addIngredient(
                            name = "spaghetti",
                            type = IngredientType.Grains,
                            position = 5,
                            unit = UnitEnum.Gram,
                            value = 400,
                        )
                        pastaRecipe.addIngredient(
                            name = "salt",
                            type = IngredientType.Spice,
                            position = 6,
                            unit = UnitEnum.ToTaste,
                            value = 1,
                        )
                        pastaRecipe.addIngredient(
                            name = "pepper",
                            type = IngredientType.Spice,
                            position = 7,
                            unit = UnitEnum.Pinch,
                            value = 1,
                        )
                        pastaRecipe.addIngredient(
                            name = "carrot",
                            type = IngredientType.Vegetables,
                            position = 8,
                            unit = UnitEnum.Gram,
                            value = 100,
                        )


                        pastaRecipe.addStep(
                            description = "Dice the onion",
                            position = 0
                        )
                        pastaRecipe.addStep(
                            description = "Chop the 2 garlic cloves",
                            position = 1
                        )
                        pastaRecipe.addStep(
                            description = "Slice the 100 gram of carrot into thin slices",
                            position = 2
                        )
                        pastaRecipe.addStep(
                            description = "use the stock cube to prepare 500ml of hot stock",
                            position = 3
                        )
                        pastaRecipe.addStep(
                            description = "Heat 1 tablespoon of olive oil in a large frying pan until hot",
                            position = 4
                        )
                        pastaRecipe.addStep(
                            description = "add the 400 gram of minced beef to the frying pan",
                            position = 5
                        )
                        pastaRecipe.addStep(
                            description = "add a pinch of pepper and salt to taste to the beef",
                            position = 6
                        )
                        pastaRecipe.addStep(
                            description = "Cook the minced beef on medium heat until well browned",
                            position = 7
                        )
                        pastaRecipe.addStep(
                            description = "transfer the minced beef to a bowl and set aside",
                            position = 8
                        )
                        pastaRecipe.addStep(
                            description = "Heat 1 tablespoon of olive oil the same frying pan until hot",
                            position = 9
                        )
                        pastaRecipe.addStep(
                            description = "add the diced onion and a pinch of salt and cook for 6 minutes on medium heat",
                            position = 10,
                            timerSeconds = 6.minutesToSeconds()
                        )
                        pastaRecipe.addStep(
                            description = "add the chopped garlic and cook for 2 more minutes",
                            position = 11,
                            timerSeconds = 2.minutesToSeconds()
                        )
                        pastaRecipe.addStep(
                            description = "add the sliced carrot to the pan",
                            position = 12
                        )
                        pastaRecipe.addStep(
                            description = "return the minced meat to the pan",
                            position = 13
                        )
                        pastaRecipe.addStep(
                            description = "add the 800 gram of canned tomato cubes to the pan and stir",
                            position = 14
                        )
                        pastaRecipe.addStep(
                            description = "add the stock to the pan and let simmer for 20 minutes",
                            position = 15,
                            timerSeconds = 20.minutesToSeconds()
                        )
                        pastaRecipe.addStep(
                            description = "Cook the spaghetti while letting the sauce simmer for another 10 minutes",
                            position = 16,
                            timerSeconds = 10.minutesToSeconds()
                        )
                        pastaRecipe.addStep(
                            description = "drain the spaghetti and serve with the sauce",
                            position = 17
                        )

                        
                        /////////

                        val curryRecipeId = insertRecipe (
                            Recipe(
                            title = "Indian Curry",
                            imageResourceUri = "example_curry",
                            description = "Hot and spicy",
                            cuisine = "Indian",
                            recipeType = "Dinner",
                            numberPortions = 4,
                            level = 2,
                            minutesDuration = 40
                            )
                        )
                        
                       val curryRecipe = getRecipeById(curryRecipeId)

                       curryRecipe.addIngredient(
                            name = "olive oil",
                            type = IngredientType.Fats,
                            position = 0,
                            unit = UnitEnum.TableSpoon,
                            value = 2,
                        )
                       curryRecipe.addIngredient(
                            name = "paneer",
                            type = IngredientType.MeatFish,
                            position = 1,
                            unit = UnitEnum.Gram,
                            value = 250,
                        )
                       curryRecipe.addIngredient(
                            name = "onion",
                            type = IngredientType.Vegetables,
                            position = 2,
                            value = 2,
                        )
                       curryRecipe.addIngredient(
                            name = "chilli flakes",
                            type = IngredientType.Spice,
                            position = 3,
                            unit = UnitEnum.TeaSpoon,
                            value = 1,
                        )
                       curryRecipe.addIngredient(
                            name = "curry powder",
                            type = IngredientType.Spice,
                            position = 4,
                            unit = UnitEnum.TableSpoon,
                            value = 1,
                        )
                       curryRecipe.addIngredient(
                            name = "canned diced tomatoes",
                            type = IngredientType.Vegetables,
                            position = 5,
                            unit = UnitEnum.Gram,
                            value = 200,
                        )
                       curryRecipe.addIngredient(
                            name = "rice",
                            type = IngredientType.Grains,
                            position = 6,
                            unit = UnitEnum.Gram,
                            value = 200,
                        )
                       curryRecipe.addIngredient(
                            name = "potatoes",
                            type = IngredientType.Grains,
                            position = 7,
                            unit = UnitEnum.Gram,
                            value = 250,
                        )
                       curryRecipe.addIngredient(
                            name = "stock cube",
                            type = IngredientType.Spice,
                            position = 8,
                            value = 1,
                        )
                        curryRecipe.addIngredient(
                            name = "salt",
                            type = IngredientType.Spice,
                            position = 9,
                            unit = UnitEnum.ToTaste,
                            value = 1,
                        )
                        curryRecipe.addIngredient(
                            name = "pepper",
                            type = IngredientType.Spice,
                            position = 10,
                            unit = UnitEnum.Pinch,
                            value = 1,
                        )

                        curryRecipe.addStep(
                            description = "wash the rice with cold water until the water is almost clear",
                            position = 0
                        )
                        curryRecipe.addStep(
                            description = "add the rice to a cooking pot together with 300mL of water and continue with the rest of the recipe",
                            position = 1
                        )
                        curryRecipe.addStep(
                            description = "Crush the 2 garlic cloves",
                            position = 2
                        )
                        curryRecipe.addStep(
                            description = "Peel the 250 gram of potatoes and cut into small cubes",
                            position = 3
                        )

                        curryRecipe.addStep(
                            description = "Cut the 250 gram of paneer into small cubes",
                            position = 4
                        )
                        curryRecipe.addStep(
                            description = "use the stock cube to prepare 500ml of hot stock",
                            position = 5
                        )
                        curryRecipe.addStep(
                            description = "Heat the 2 tablespoons of oil in a frying pan until hot",
                            position = 6
                        )
                        curryRecipe.addStep(
                            description = "Fry the cubes of paneer for 2 minutes, then remove from the frying pan",
                            position = 7,
                            timerSeconds = 2.minutesToSeconds()
                        )
                        curryRecipe.addStep(
                            description = "Fry the onions in the frying pan until golden brown",
                            position = 8
                        )
                        curryRecipe.addStep(
                            description = "Add the crushed garlic to the pan",
                            position = 9
                        )
                        curryRecipe.addStep(
                            description = "Add 1 teaspoon chili flakes to the pan",
                            position = 10
                        )
                        curryRecipe.addStep(
                            description = "Add 1 teaspoon curry powder to the pan",
                            position = 11
                        )
                        curryRecipe.addStep(
                            description = "Add 2 tablespoons of water to the pan",
                            position = 12
                        )
                        curryRecipe.addStep(
                            description = "fry for 2 minutes on low heat",
                            position = 13,
                            timerSeconds = 2.minutesToSeconds()
                        )
                        curryRecipe.addStep(
                            description = "add the potato cubes to the pan",
                            position = 14
                        )
                        curryRecipe.addStep(
                            description = "add the 200g canned tomato dices to the pan",
                            position = 15
                        )
                        curryRecipe.addStep(
                            description = "wait for the ingredients to start to simmer",
                            position = 16
                        )
                        curryRecipe.addStep(
                            description = "cover the frying pan with a lid and let simmer for 15 minutes, in the meanwhile boil the rice on low heat",
                            position = 17,
                            timerSeconds = 15.minutesToSeconds()
                        )
                        curryRecipe.addStep(
                            description = "add the paneer dices to the frying pan and stir",
                            position = 18,
                        )
                        curryRecipe.addStep(
                            description = "heat through for 4 minutes",
                            position = 19,
                            timerSeconds = 4.minutesToSeconds()
                        )
                        curryRecipe.addStep(
                            description = "serve the curry with the rice",
                            position = 20,
                        )

                    }
                }
            }
        }
    }
}