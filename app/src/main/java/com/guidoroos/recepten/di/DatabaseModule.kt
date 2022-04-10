package com.guidoroos.recepten.di

import android.content.Context
import androidx.room.Room
import com.guidoroos.recepten.db.RecipeDao
import com.guidoroos.recepten.db.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideRecipeDao(database:RecipeDatabase): RecipeDao {
        return database.recipeDao
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RecipeDatabase {

        Room.databaseBuilder(
            appContext.applicationContext,
            RecipeDatabase::class.java,
            "recipe_database"
        )
            .fallbackToDestructiveMigration()
            .addCallback(RecipeDatabase.RecipeDatabaseCallback(provideRecipeDao()))
            .build()

        return Room.databaseBuilder(
            appContext,
            RecipeDatabase::class.java,
            "RssReader"
        ).build()
    }
}