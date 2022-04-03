package com.guidoroos.recepten.ui.grocery

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.guidoroos.recepten.R

class GroceryFragment : Fragment() {

    companion object {
        fun newInstance() = GroceryFragment()
    }

    private lateinit var viewModel: GroceryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grocery_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroceryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}