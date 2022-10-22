package com.example.ecommerceapplication.ui.home.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Category
import com.example.domain.models.Product
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentCategoryBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.category.CategoryViewModel
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import com.facebook.shimmer.ShimmerFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Display single category items in a page
 */
@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CategoryViewModel by hiltNavGraphViewModels(R.id.categoryFragment)

    private lateinit var productsLoader: ShimmerFrameLayout
    private lateinit var productsRv: RecyclerView

    /** Get the maximum product count to load at a time */
    private val PRODUCTS_COUNT = 100
    private lateinit var category: Category

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        category = (arguments?.get(CATEGORY_OBJECT) as Category)

        // Toolbar
        val toolbar = binding.categoryToolbar.root
        toolbar.title = category.categoryTitle
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productsUrl = category.productsUrl
        productsRv = binding.categoryProducts
        productsLoader = binding.categoryProductsLoader
        showLoading()

        kotlin.runCatching {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.fetchProducts(productsUrl, category.categoryId, PRODUCTS_COUNT).onSuccess {
                    showProducts(it)
                }.onFailure {
                    productsLoader.visibility = GONE
                    Toast.makeText(context, it.localizedMessage, Toast.LENGTH_SHORT)
                }
            }
        }.onFailure {
            productsLoader.visibility = GONE
            Toast.makeText(
                context,
                "Products retrieval failed. Check your internet connection",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun showLoading() {
        productsLoader.startShimmerAnimation()
    }

    private fun showProducts(products: List<Product>) {
        productsLoader.stopShimmerAnimation()
        productsLoader.visibility = GONE
        productsRv.visibility = VISIBLE

        productsRv.initRecyclerView(
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false),
            ProductRecyclerAdapter(
                products,
                requireContext(),
                onItemClicked = { product ->
                    val bundle = bundleOf(PRODUCT_OBJECT to product)
                    findNavController().navigate(
                        R.id.action_categoryFragment_to_productFragment,
                        bundle
                    )
                })
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CATEGORY_OBJECT = "categoryObject"
        fun newInstance() = CategoryFragment()
    }
}