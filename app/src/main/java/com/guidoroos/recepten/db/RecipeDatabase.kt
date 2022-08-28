package com.guidoroos.recepten.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.guidoroos.recepten.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        GroceryList::class,
        Ingredient::class,
        Item::class,
        ItemType::class,
        Recipe::class,
        RecipeIngredient::class,
        Step::class,
        Unit::class,
        UnitType::class],
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
                    val recipeDao = database.recipeDao


                    recipeDao.insertAllItemTypes(
                        *listOf(
                            ItemType(
                                resourceId = R.string.vegetables
                            ),
                            ItemType(
                                resourceId = R.string.meats_fish_substitutes
                            ),
                            ItemType(
                                resourceId = R.string.dairy
                            ),
                            ItemType(
                                resourceId = R.string.grains_rice
                            ),
                            ItemType(
                                resourceId = R.string.dairy
                            ),
                            ItemType(
                                resourceId = R.string.spices
                            ), ItemType(
                                resourceId = R.string.fats_oils
                            ), ItemType(
                                resourceId = R.string.nuts_baking_products
                            ), ItemType(
                                resourceId = R.string.beans_legumes
                            ), ItemType(
                                resourceId = R.string.fruits
                            )
                        ).toTypedArray()
                    )


                    recipeDao.insertRecipe(
                        Recipe(
                            title = "Pasta Bolognese",
                            imageResourceUri = "example_pasta",
                            description = "Italian Pasta",
                            cuisine = "Italian",
                            recipeType = "Dinner",
                            level = 1,
                            minutesDuration = 25
                        )
                    )

                    recipeDao.insertIngredient(Ingredient(
                        value =
                        unitId =,
                        itemId =,
                    ))

                    recipeDao.insertRecipe(
                        Recipe(
                            title = "Indian Curry",
                            imageResourceUri = "example_curry",
                            description = "Hot and spicy",
                            cuisine = "Indian",
                            recipeType = "Dinner",
                            level = 2,
                            minutesDuration = 40
                        )
                    )
                }
            }
        }
    }
}