package com.guidoroos.recepten.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//exportschema is for archiving schema, version need update when update db
//volatile means no caching, so all threads from one place so same values
//synchronized means lock for access
//.fallbackToDestructiveMigration() can be replaced by migration strategy


@Database(entities = [Cuisine::class,
        GroceryList::class,
        Ingredient::class,
        Item::class,
        ItemType::class,
        OperationType::class,
        Recipe::class,
        RecipeIngredient::class,
        RecipeStep::class,
        RecipeType::class,
        Step::class,
        StepIngredient::class,
        Unit::class,
        UnitType::class],
    version = 1, exportSchema = false)

public abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipe_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}