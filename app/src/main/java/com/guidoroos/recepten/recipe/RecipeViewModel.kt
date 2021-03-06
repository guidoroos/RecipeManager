package com.guidoroos.recepten.recipe

import androidx.lifecycle.ViewModel
import com.guidoroos.recepten.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository):
    ViewModel()   {

}