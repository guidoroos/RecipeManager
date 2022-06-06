package com.guidoroos.recepten.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository):
    ViewModel()   {

    suspend fun getRecipeTypeName(id: Long): String {
        return withContext(Dispatchers.Default) {
                repository.getRecipeTypeName(id)
            }
    }

    suspend fun getCuisineName(id: Long): String {
        return withContext(Dispatchers.Default) {
                repository.getCuisineName(id)
            }
    }
}