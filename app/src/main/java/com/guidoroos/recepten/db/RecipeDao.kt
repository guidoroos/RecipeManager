package com.guidoroos.recepten.db

import androidx.room.*
import com.guidoroos.recepten.data.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe ): Long

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe ORDER BY title")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id:Long): Recipe



    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredient(ingredient: Ingredient):Long

    private suspend fun insertIngredient (name:String, type:IngredientType):Long {
        val ingredient = Ingredient(name=name, typeRes = type.resourceId )
        return insertIngredient(ingredient)
    }

    suspend fun Recipe.addIngredient(
        name: String,
        type: IngredientType,
        position: Int,
        unit: UnitEnum = UnitEnum.Unit,
        value: Int,
        valueDenominator:Int = 1
    ) {
        val ingredientId = insertIngredient(name, type)
        val ingredient = getIngredientFromId(ingredientId)

        insertRecipeIngredient(
            ingredient = ingredient,
            recipe = this,
            position = position,
            unit = unit,
            value = value,
            valueDenominator
        )
    }

    @Query("SELECT * FROM ingredient WHERE id = :id")
    fun getIngredientFromId(id:Long): Ingredient

    @Update
    suspend fun updateIngredient(ingredient:Ingredient)

    @Delete
    suspend fun deleteIngredient(ingredient:Ingredient
    )

    @Query("SELECT id FROM ingredient WHERE name = :name")
    fun getIngredientId(name:String): Long?
    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(recipeIngredient: RecipeIngredient)

    @Query("SELECT * FROM recipe_ingredient WHERE recipeId == :recipeId")
    fun getIngredientsForRecipe (recipeId:Long):List<RecipeIngredient>

    @Update
    suspend fun updateRecipeIngredient(recipeIngredient:RecipeIngredient)

    @Delete
    suspend fun deleteRecipeIngredient(recipeIngredient:RecipeIngredient
    )

    suspend fun insertRecipeIngredient (
        ingredient:Ingredient,
        recipe:Recipe,
        position:Int,
        unit:UnitEnum,
        value:Int,
        valueDenominator: Int = 1
    ) {
        val recipeIngredient = RecipeIngredient (
        recipeId = recipe.id,
        ingredientId = ingredient.id,
        position = position,
        value = value,
        valueDenominator = valueDenominator,
        unit= unit.name
        )

        insertRecipeIngredient (recipeIngredient)
    }

    //

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStep(step: RecipeStep):Long

    @Update
    suspend fun updateStep(step: RecipeStep)

    @Delete
    suspend fun deleteStep(step: RecipeStep)

    suspend fun Recipe.addStep (
        description: String,
        position: Int,
        timerSeconds: Int? = null
    ) {
        val step = RecipeStep (
            recipeId = this.id,
            description = description,
            position = position,
            timerSeconds = timerSeconds
        )
        insertStep (step)
    }

    @Query("SELECT * FROM step WHERE recipeId == :recipeId")
    fun getStepsForRecipe (recipeId:Long):List<RecipeStep>




//
//    @Update
//    suspend fun update(: )
//
//    @Delete
//    suspend fun delete(: )
//
//    @Query("SELECT * FROM ")
//    fun getAll(): List<>

}