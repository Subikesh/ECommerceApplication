package com.example.ecommerceapplication.ui.home.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.data.api.GetCategoryDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.ProductsList
import com.example.data.repository.ProductMapper.fromApiModel
import com.example.ecommerceapplication.MainActivity
import com.example.ecommerceapplication.databinding.FragmentCategoryBinding
import com.example.ecommerceapplication.extensions.initRecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val CATEGORY_TITLE = "title"
const val CATEGORY_ID = "categoryId"

/**
 * Display single category items in a page
 */
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    /** Get the maximum product count to load at a time */
    private val PRODUCTS_COUNT = 50

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

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

        val service = RetrofitInstance.retrofitInstance?.create(GetCategoryDataService::class.java)
        val call = service?.getProductsList(productsUrl)
        var productList: List<com.example.data.api.models.Product>

        call?.enqueue(object : Callback<ProductsList?> {
            override fun onResponse(call: Call<ProductsList?>?, response: Response<ProductsList?>) {
                if(response.code() == 200) {
                    productList = response.body()?.products!!.subList(0, PRODUCTS_COUNT)
                    Log.d("API response", "Products retrieved")
                    Log.d("API response", "${response.raw()}")
                    val productObjects = mutableListOf<com.example.data.models.Product>()
                    for (product in productList) {
                        productObjects.add(fromApiModel(product))
                    }
                    binding.loaderImage.visibility = GONE
                    rvProducts.initRecyclerView(
                        GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false),
                        ProductRecyclerAdapter(productObjects.toTypedArray(), requireContext())
                    )
                } else {
                    Toast.makeText(context, "Products not retrieved", Toast.LENGTH_SHORT).show()
                    Log.d("API response", "Product retrieval failed")
                    Log.d("API response", "${response.raw()}")
                }
            }

            override fun onFailure(call: Call<ProductsList?>?, t: Throwable) {
                Toast.makeText(context, "Products not retrieved", Toast.LENGTH_SHORT).show()
                Log.d("API response", "Product retrieval failed")
                Log.d("API response", "$t")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CategoryFragment()
    }
}