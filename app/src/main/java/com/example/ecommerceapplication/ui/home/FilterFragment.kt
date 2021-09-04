package com.example.ecommerceapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.databinding.FragmentFilterBinding

/**
 * A fragment representing a list of Filters.
 */
class FilterFragment : Fragment() {

    companion object {
        val categoryList =
            mutableListOf("Mobiles", "Electronics", "Groceries", "Fashion", "Furniture", "Kitchen products")
        fun newInstance() = FilterFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFilterBinding.inflate(inflater, container, false)
        val view = binding.root

        // Toolbar
        val toolbar = binding.filtersToolbar.root
        toolbar.title = "Filters"
        (activity as MainActivity).setSupportActionBar(toolbar)

        // Creating dropdown menu for category selection
        val spinner = binding.filterCategorySpinner
        val adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, categoryList) }
        spinner.adapter = adapter

        val text: String = spinner.selectedItem.toString()
        Log.i("FilterFragment", "$text is selected");
        return view
    }
}