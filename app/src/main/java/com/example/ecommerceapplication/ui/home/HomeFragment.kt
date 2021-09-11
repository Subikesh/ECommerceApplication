package com.example.ecommerceapplication.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.entities.categoryList
import com.example.ecommerceapplication.MainActivity
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
        // Products card group
        val rvProducts = binding.homeRecyclerView
        val categoryAdapter = HomeCategoryAdapter(categoryList, requireContext())

        rvProducts.initRecyclerView(LinearLayoutManager(requireContext()), categoryAdapter)
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