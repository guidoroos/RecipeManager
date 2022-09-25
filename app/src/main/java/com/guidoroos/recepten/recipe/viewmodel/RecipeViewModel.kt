package com.guidoroos.recepten.recipe.viewmodel

import androidx.lifecycle.*
import com.guidoroos.recepten.data.IngredientType
import com.guidoroos.recepten.data.IngredientViewData
import com.guidoroos.recepten.data.UnitEnum
import com.guidoroos.recepten.db.Ingredient
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.db.RecipeIngredient
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository):
    ViewModel()   {

    private val _recipeIngredients = MutableLiveData<List<IngredientViewData>>()
    val recipeIngredients: LiveData<List<IngredientViewData>>
        get() = _recipeIngredients

    fun getRecipeIngredients (recipe:Recipe?) {
        if (recipe != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = repository.getRecipeIngredients(recipe)
                _recipeIngredients.postValue(result)
            }
        }
    }

    var currentPhotoPath: String? = null
    var minutesDuration: Int = 0
    var levelSelected: Int = 0

    fun storeRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.storeRecipe(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.updateRecipe(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.deleteRecipe(recipe)
        }
    }




}