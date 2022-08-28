package com.guidoroos.recepten.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidoroos.recepten.db.Recipe
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository):
    ViewModel()   {

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