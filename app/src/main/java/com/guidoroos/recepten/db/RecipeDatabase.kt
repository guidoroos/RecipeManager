package com.guidoroos.recepten.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.guidoroos.recepten.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

//exportschema is for archiving schema, version need update when update db
//volatile means no caching, so all threads from one place so same values
//synchronized means lock for access
//.fallbackToDestructiveMigration() can be replaced by migration strategy


@Database(
    entities = [Cuisine::class,
        GroceryList::class,
        Ingredient::class,
        Item::class,
        ItemType::class,
        Recipe::class,
        RecipeIngredient::class,
        RecipeType::class,
        Step::class,
        Unit::class,
        UnitType::class],
    version = 1, exportSchema = false
)

abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao

    @Inject val instance:RecipeDatabase
        get() {
            TODO()
        }


    private class RecipeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val recipeDao = database.recipeDao

                    recipeDao.apply {

                        insertAll(
                            RecipeType(name = "Breakfast"),
                            RecipeType(name = "Lunch"),
                            RecipeType(name = "Dinner"),
                        )

                        insert(
                            Cuisine(
                                name = "Italian",
                                imageResourceId = R.drawable.italian_flag
                            )
                        )

                        recipeDao.insert(
                            Recipe(
                                title = "Pasta Bolognese",
                                imageResourceId = R.drawable.pasta_svgrepo_com,
                                description = "Italian Pasta",
                                cuisineId = getCuisineId("Italian"),
                                recipeTypeId = getRecipeTypeId("Dinner")
                            )
                        )
                    }
                }
            }
        }
    }
}