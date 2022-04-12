package com.guidoroos.recepten.filter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guidoroos.recepten.R

class RecipeFilterFragment : Fragment() {

    private lateinit var viewModel: RecipeFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(RecipeFilterViewModel::class.java)

        return inflater.inflate(R.layout.recipe_filter_fragment, container, false)
    }


}