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
import com.example.ecommerceapplication.databinding.FragmentHomeBinding

/**
 * A fragment representing a list of Filters.
 */
class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        val view = binding.root

        // Toolbar
        val toolbar = binding.filtersToolbar.root
        toolbar.title = "Filters"
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Creating dropdown menu for category selection
        val spinner = binding.filterCategorySpinner
        val adapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, categoryList) }
        spinner.adapter = adapter

        val text: String = spinner.selectedItem.toString()
        Log.i("FilterFragment", "$text is selected")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val categoryList =
            mutableListOf(
                "Mobiles",
                "Electronics",
                "Groceries",
                "Fashion",
                "Furniture",
                "Kitchen products"
            )

        fun newInstance() = FilterFragment()
    }
}