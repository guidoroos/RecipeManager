package com.guidoroos.recepten.recipe.ui.step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.guidoroos.recepten.R
import com.guidoroos.recepten.recipe.viewmodel.RecipeViewModel

class RecipeStepFragment : Fragment() {


    private val viewModel: RecipeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.recipe_fragment, container, false)
    }


}