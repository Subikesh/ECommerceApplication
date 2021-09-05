package com.example.ecommerceapplication.ui.home.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.databinding.FragmentCategoryBinding

/**
 * Display single category items in a page
 */
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        // Toolbar
        val toolbar = binding.categoryToolbar.root
        toolbar.title = "User Profile"
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryTitle.text = arguments?.getString("title")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }
}