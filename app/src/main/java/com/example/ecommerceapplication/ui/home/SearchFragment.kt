package com.example.ecommerceapplication.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentSearchBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.products.ProductRecyclerAdapter
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val SEARCH_QUERY = "searchQuery"

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.searchFragment)
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    //TODO: Move this to viewmodel with live data
    private var searchQuery: String? = null

    private lateinit var searchToolbar: Toolbar
    private lateinit var rvAdapter: ProductRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        if (searchQuery == null)
            searchQuery = arguments?.get(SEARCH_QUERY) as String

        searchToolbar = binding.searchToolbar
        searchToolbar.title = getString(R.string.app_name)
        searchToolbar.title = "Search results for '$searchQuery'"
        setHasOptionsMenu(true)
        (activity as MainActivity).setSupportActionBar(searchToolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val products = viewModel.searchProducts(searchQuery ?: "")
            rvAdapter = ProductRecyclerAdapter(
                products,
                requireContext(),
                onItemClicked = { product ->
                    val bundle = bundleOf(PRODUCT_OBJECT to product)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_productFragment,
                        bundle
                    )
                })
            binding.searchResults.initRecyclerView(
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false),
                rvAdapter
            )
            if (products.isEmpty())
                Toast.makeText(context, "No products found!", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_toolbar_menu, menu)
        val searchView = menu.findItem(R.id.search_button).actionView as SearchView
        searchView.queryHint = "Type here to search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onQueryTextSubmit(search: String?): Boolean {
                requireActivity().currentFocus?.let { view ->
                    val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
                }
                return if (search != null) {
                    lifecycleScope.launch {
                        val products = viewModel.searchProducts(search)
                        rvAdapter.productList = products
                        searchQuery = search
                        searchToolbar.title = "Search results for '$search'"
                        rvAdapter.notifyDataSetChanged()
                        if (products.isEmpty())
                            Toast.makeText(context, "No products found!", Toast.LENGTH_SHORT).show()
                    }
                    true
                } else false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}