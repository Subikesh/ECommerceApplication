package com.example.ecommerceapplication.ui.home.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.databinding.FragmentCategoryBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.HomeViewModel

const val CATEGORY_TITLE = "title"
const val CATEGORY_ID = "categoryId"

/**
 * Display single category items in a page
 */
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    /** Get the maximum product count to load at a time */
    private val PRODUCTS_COUNT = 50

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Toolbar
        val toolbar = binding.categoryToolbar.root
        toolbar.title = arguments?.get(CATEGORY_TITLE) as CharSequence?
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = arguments?.get(CATEGORY_ID) as String
        // TODO: Try to get productsUrl another way
        // TODO: Try to send category object as argument
        val productsUrl = arguments?.get("productsUrl") as String
        val rvProducts = binding.categoryProducts
        val productsLoader = binding.categoryProductsLoader
        productsLoader.startShimmerAnimation()

        viewModel.loadProducts(productsUrl, PRODUCTS_COUNT).observe(viewLifecycleOwner) { products ->
            if (products != null) {
                productsLoader.stopShimmerAnimation()
                productsLoader.visibility = GONE
                rvProducts.visibility = VISIBLE

                rvProducts.initRecyclerView(
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false),
                    ProductRecyclerAdapter(products, requireContext())
                )
            } else {
                productsLoader.visibility = GONE
                Toast.makeText(context, "Products not retrieved", Toast.LENGTH_SHORT).show()
                Log.d("API response", "Product retrieval failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }
}