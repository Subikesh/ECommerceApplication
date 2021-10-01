package com.example.ecommerceapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentHomeBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.extensions.observeOnce
import com.example.ecommerceapplication.ui.home.products.HomeCategoryAdapter
import com.example.data.roomdb.entities.MutablePair
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var isScrolling = false
    var scrolledOutItems: Int = 0
    var currentItemCount: Int = 0
    var totalItems: Int = 0

    private val COUNT_ON_LOAD_MORE = 10

    private lateinit var homeAdapter: HomeCategoryAdapter

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
            viewModel.loadCategories().observeOnce(viewLifecycleOwner) { categories ->
                if (categories != null) {
                    for (category in categories) {
                        if (viewModel.categoryList != null)
                            viewModel.categoryList?.add(MutablePair(category, null))
                        else
                            viewModel.categoryList = mutableListOf(MutablePair(category, null))
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

        homeAdapter = HomeCategoryAdapter(requireContext(), viewModel) { product ->
            val bundle = bundleOf(PRODUCT_OBJECT to product)
            findNavController().navigate(
                R.id.action_navigation_home_to_productFragment,
                bundle
            )
        }
        val manager = LinearLayoutManager(requireContext())
        rvCategories.initRecyclerView(manager, homeAdapter)

        /** Adding load more on scrolling to the bottom of the page */
        rvCategories.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Items scrolled away from screen on top
                scrolledOutItems = manager.findFirstCompletelyVisibleItemPosition()
                // Items currently shown in screen
                currentItemCount = manager.childCount
                // Items loaded by recycler view
                totalItems = manager.itemCount
                if (isScrolling && ((currentItemCount + scrolledOutItems) == totalItems) && viewModel.categoryList!!.size <= 57) {
                    isScrolling = false
                    binding.rvLoaderProgress.visibility = View.VISIBLE

                    // Fetching api data
                    viewModel.loadMoreCategories(COUNT_ON_LOAD_MORE)
                        .observe(viewLifecycleOwner) { categoryList ->
                            if (categoryList != null && categoryList.size > totalItems) {
                                Log.d(
                                    "LastViewModel",
                                    "${viewModel.categoryList!!.size} and ${homeAdapter.emptyCategoryCount} ${viewModel.categoryList!!.last().first}"
                                )
                                for (category in categoryList.subList(
                                    viewModel.categoryList!!.size + homeAdapter.emptyCategoryCount,
                                    categoryList.size
                                )) {
                                    viewModel.categoryList?.add(MutablePair(category, null))
                                    homeAdapter.notifyItemInserted(viewModel.categoryList!!.size - 1)
                                    binding.rvLoaderProgress.visibility = View.INVISIBLE
                                }
                            }
                        }
                } else if (isScrolling && totalItems == 58)
                    Toast.makeText(context, "End of category list", Toast.LENGTH_SHORT).show()
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
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                return if (s != null) {
                    val bundle = bundleOf(SEARCH_QUERY to s)
                    findNavController().navigate(
                        R.id.action_navigation_home_to_searchFragment,
                        bundle
                    )
                    true
                } else false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                return true
            }
        })

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