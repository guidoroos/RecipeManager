package com.guidoroos.recepten.ui.filter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guidoroos.recepten.R

class RecipeFilterFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeFilterFragment()
    }

    private lateinit var viewModel: RecipeFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipe_filter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipeFilterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}