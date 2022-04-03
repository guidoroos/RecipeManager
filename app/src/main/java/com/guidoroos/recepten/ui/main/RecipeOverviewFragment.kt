package com.guidoroos.recepten.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guidoroos.recepten.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeOverviewFragment : Fragment() {

    private lateinit var viewModel: RecipeOverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(RecipeOverviewViewModel::class.java)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


}