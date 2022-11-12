package com.example.ecommerceapplication.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Category
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentHomeBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.products.HomeCategoryAdapter
import com.example.ecommerceapplication.ui.product.PRODUCT_OBJECT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.navigation_home)
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var isScrolling = false
    var scrolledOutItems: Int = 0
    var currentItemCount: Int = 0
    var totalItems: Int = 0

    private val COUNT_ON_LOAD_MORE = 10

    // Total categories that will be shown in homepage
    private val TOTAL_CATEGORIES = 55

    private lateinit var homeAdapter: HomeCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    private fun loadHomepage(forceReload: Boolean = false) {
        val categoryShimmer = binding.categoryLoader

        CoroutineScope(Dispatchers.Main).launch {
            val categoryList = viewModel.fetchCategories(forceReload)
            Log.d("Categories", "Initial category fetch: $categoryList")

            /** Loading all categories to be shown in homepage */
            if (categoryList.isFailure) {
                categoryShimmer.visibility = View.GONE
                Toast.makeText(
                    context,
                    "Categories not retrieved. Check your internet connection.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            initializeCategories(categoryList.getOrDefault(listOf()), forceReload)
        }
    }

    /**
     * Initialize categories recyclerview in homepage
     * Stops the shimmer loader and displays categories
     */
    private fun initializeCategories(pCategories: List<Category>, forceReload: Boolean = false) {
        val categories = pCategories.toMutableList()
        val rvCategories = binding.homeRecyclerView
        val categoryShimmer = binding.categoryLoader

        categoryShimmer.stopShimmerAnimation()
        categoryShimmer.visibility = View.GONE
        rvCategories.visibility = View.VISIBLE

        homeAdapter = HomeCategoryAdapter(categories, requireContext(), viewModel, forceReload) { product ->
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
                if (isScrolling && ((currentItemCount + scrolledOutItems) == totalItems) && categories.size <= TOTAL_CATEGORIES) {
                    isScrolling = false
                    binding.rvLoaderProgress.visibility = View.VISIBLE

                    // Fetching api data
                    viewModel.loadMoreCategories(COUNT_ON_LOAD_MORE)
                        .observe(viewLifecycleOwner) { categoryList ->
                            if (categoryList != null && categoryList.size > totalItems) {
                                for (category in categoryList.subList(categories.size + homeAdapter.emptyCategoryCount, categoryList.size)) {
                                    categories.add(category)
                                    homeAdapter.notifyItemInserted(categories.size - 1)
                                    binding.rvLoaderProgress.visibility = View.INVISIBLE
                                }
                            }
                        }
                }
            }
        })
    }

    /** Inflating search in actionbar */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu)
        val searchView = menu.findItem(R.id.home_search).actionView as SearchView
        searchView.queryHint = "Type here to search..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String?): Boolean {
                requireActivity().currentFocus?.let { view ->
                    val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    inputManager?.hideSoftInputFromWindow(view.windowToken, 0)
                }
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
                loadHomepage(true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}