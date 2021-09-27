package com.example.ecommerceapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentHomeBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.products.HomeCategoryAdapter

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Toolbar
        val toolbar = binding.homeToolbar
        toolbar.title = getString(R.string.app_name)
        setHasOptionsMenu(true)
        (activity as MainActivity).setSupportActionBar(toolbar)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoryLoader.startShimmerAnimation()

        loadHomepage()
    }

    private fun loadHomepage() {
        val categoryShimmer = binding.categoryLoader

        /** Loading all categories to be shown in homepage */
        if (viewModel.categoryList != null) {
            initializeCategories()
        } else {
            viewModel.loadCategories().observe(viewLifecycleOwner) { categories ->
                if (categories != null) {
                    for (category in categories) {
                        if (viewModel.categoryList != null)
                            viewModel.categoryList!![category] = null
                        else
                            viewModel.categoryList = hashMapOf(category to null)
                    }
                    initializeCategories()
                } else {
                    categoryShimmer.visibility = View.GONE
                    Toast.makeText(context, "Categories not retrieved", Toast.LENGTH_SHORT).show()
                    Log.d("API response", "Category retrieval failed")
                }
            }
        }
    }

    private fun initializeCategories() {
        val rvCategories = binding.homeRecyclerView
        val categoryShimmer = binding.categoryLoader

        categoryShimmer.stopShimmerAnimation()
        categoryShimmer.visibility = View.GONE
        rvCategories.visibility = View.VISIBLE

        val categoryAdapter =
            HomeCategoryAdapter(viewModel.categoryList!!.keys.toMutableList(), requireContext(), viewModel) { product ->
                val bundle = bundleOf(PRODUCT_OBJECT to product)
                findNavController().navigate(
                    R.id.action_navigation_home_to_productFragment,
                    bundle
                )
            }
        rvCategories.initRecyclerView(
            LinearLayoutManager(requireContext()),
            categoryAdapter
        )
    }

    /**
     * Inflating search and filter menu to actionbar
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu)
        val searchView = menu.findItem(R.id.home_search).actionView as SearchView
        searchView.queryHint = "Type here to search..."

        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Handling onclick listeners when toolbar icons are clicked
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_reload -> {
                viewModel.categoryList = null
                findNavController().navigate(R.id.action_navigation_home_self)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}