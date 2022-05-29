package com.guidoroos.recepten.di

import android.content.Context
import com.guidoroos.recepten.db.RecipeDao
import com.guidoroos.recepten.db.RecipeDatabase
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideRecipeDao(database:RecipeDatabase): RecipeDao {
        return database.recipeDao
    }

    @Provides
    fun provideRepository(dao:RecipeDao): RecipeRepository {
        return RecipeRepository(dao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RecipeDatabase {
        return RecipeDatabase.getInstance(appContext, CoroutineScope(Dispatchers.IO))
    }
}