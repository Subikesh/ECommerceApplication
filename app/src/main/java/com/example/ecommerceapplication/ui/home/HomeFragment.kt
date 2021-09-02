package com.example.ecommerceapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.entities.categoryList
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.databinding.FragmentHomeBinding
import com.example.ecommerceapplication.ui.home.products.CategoryRecyclerAdapter

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvProducts = binding.homeRecyclerView
        val productAdapter = CategoryRecyclerAdapter(categoryList, requireContext())

        rvProducts.adapter = productAdapter
        rvProducts.layoutManager = LinearLayoutManager(requireContext())

        binding.filterButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_filterFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}