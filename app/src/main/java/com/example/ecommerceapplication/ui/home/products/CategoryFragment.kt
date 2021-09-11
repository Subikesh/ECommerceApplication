package com.example.ecommerceapplication.ui.home.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.entities.categoryList
import com.example.data.entities.getProductMap
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.databinding.FragmentCategoryBinding
import com.example.ecommerceapplication.extensions.initRecyclerView

const val CATEGORY_TITLE = "title"
const val CATEGORY_ID = "categoryId"

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
        toolbar.title = arguments?.get(CATEGORY_TITLE) as CharSequence?
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId= arguments?.get(CATEGORY_ID) as String
        val rvProducts = binding.categoryProducts
        val productsAdapter =
            ProductRecyclerAdapter(getProductMap(categoryList)[categoryId], requireContext())

        rvProducts.initRecyclerView(
            GridLayoutManager(
                requireContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
            ), productsAdapter
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }
}