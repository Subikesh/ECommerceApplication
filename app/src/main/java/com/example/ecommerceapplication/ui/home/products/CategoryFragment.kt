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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.di.RoomModule
import com.example.data.roomdb.entities.MutablePair
import com.example.data.repository.CategoryDatabase
import com.example.data.repository.GetCategories
import com.example.data.repository.GetProducts
import com.example.domain.models.Category
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentCategoryBinding
import com.example.ecommerceapplication.di.AppModule
import com.example.ecommerceapplication.di.DaggerAppComponent
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.HomeViewModel
import javax.inject.Inject

const val CATEGORY_OBJECT = "categoryObject"

/**
 * Display single category items in a page
 */
class CategoryFragment : Fragment() {

    @Inject
    lateinit var categoryApi: GetCategories
    @Inject
    lateinit var productsApi: GetProducts
    @Inject
    lateinit var categoryDatabase: CategoryDatabase

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    /** Get the maximum product count to load at a time */
    private val PRODUCTS_COUNT = 100
    private lateinit var categoryObj: Category

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        DaggerAppComponent.builder()
            .appModule(AppModule(requireActivity()))
            .roomModule(RoomModule(requireActivity()))
            .build().inject(this)

        val factory = HomeViewModel.Factory(categoryApi, productsApi, categoryDatabase)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        categoryObj = (arguments?.get(CATEGORY_OBJECT) as Category)

        // Toolbar
        val toolbar = binding.categoryToolbar.root
        toolbar.title = categoryObj.categoryTitle
        (activity as MainActivity).setSupportActionBar(toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productsUrl = categoryObj.productsUrl
        val rvProducts = binding.categoryProducts
        val productsLoader = binding.categoryProductsLoader
        productsLoader.startShimmerAnimation()

        viewModel.loadProducts(productsUrl, categoryObj.categoryId, PRODUCTS_COUNT)
            .observe(viewLifecycleOwner) { products ->
                if (products != null) {
                    productsLoader.stopShimmerAnimation()
                    productsLoader.visibility = GONE
                    rvProducts.visibility = VISIBLE
                    viewModel.loadCategoryDatabase(MutablePair(categoryObj, products))

                    rvProducts.initRecyclerView(
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
                } else {
                    productsLoader.visibility = GONE
                    Toast.makeText(
                        context,
                        "Products retrieval failed. Check your internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
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