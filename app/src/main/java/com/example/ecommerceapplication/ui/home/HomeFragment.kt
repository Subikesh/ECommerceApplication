package com.example.ecommerceapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.api.GetCategoryDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.CategoryResult
import com.example.data.repository.CategoryMapper
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentHomeBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.products.HomeCategoryAdapter
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryList: List<com.example.domain.models.Category>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

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

        val service = RetrofitInstance.retrofitInstance?.create(GetCategoryDataService::class.java)
        val call = service?.getCategories()

        // Products card group
        val rvCategories = binding.homeRecyclerView
        val categoryShimmer = binding.categoryLoader
        categoryShimmer.startShimmerAnimation()

        call?.enqueue(object : retrofit2.Callback<CategoryResult> {
            override fun onResponse(
                call: Call<CategoryResult>,
                response: Response<CategoryResult>
            ) {
                val categoryObjects = response.body()!!
                Log.d("API response", "Categories retrieved")
                Log.d("API response", "Home categories: ${response.raw()}")
                categoryList = CategoryMapper.fromApiModel(categoryObjects)

                Log.d("API response", "Categories: $categoryList")

                categoryShimmer.stopShimmerAnimation()
                categoryShimmer.visibility = View.GONE
                rvCategories.visibility = View.VISIBLE

                val categoryAdapter = HomeCategoryAdapter(categoryList, requireContext())
                rvCategories.initRecyclerView(
                    LinearLayoutManager(requireContext()),
                    categoryAdapter
                )
            }

            override fun onFailure(call: Call<CategoryResult>, t: Throwable) {
                categoryShimmer.visibility = View.GONE
                Toast.makeText(context, "Categories not retrieved", Toast.LENGTH_SHORT).show()
                Log.d("API response", "Category retrieval failed")
                Log.d("API response", "$t")
            }
        })
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
            R.id.home_filter -> findNavController().navigate(R.id.action_navigation_home_to_filterFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}