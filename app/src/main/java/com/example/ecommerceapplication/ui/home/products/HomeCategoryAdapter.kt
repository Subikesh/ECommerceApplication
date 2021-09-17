package com.example.ecommerceapplication.ui.home.products

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.api.GetCategoryDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.ProductsList
import com.example.data.models.Category
import com.example.data.repository.ProductMapper
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.extensions.initRecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Adapter for recycler view to display products in home page
 * This RecyclerView contains another recycler view for horizontal product cards scroller
 * @param categoryList List of strings showing the category title
 */
class HomeCategoryAdapter(private val categoryList: List<Category>, val context: Context) :
    RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    /** Number of products to be shown in a single category in homepage */
    private val MAX_PRODUCTS = 10

    /**
     * Inflate the single category UI
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_row_item, parent, false)
        return ViewHolder(view)
    }

    /**
     * Sets the text for category title and sets adapter for child product list
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currCategory = categoryList[position]
        holder.textView.text = currCategory.categoryTitle
        holder.categoryId = currCategory.categoryId
        holder.productsUrl = currCategory.productsUrl

        val service = RetrofitInstance.retrofitInstance?.create(GetCategoryDataService::class.java)
        val call = service?.getProductsList(currCategory.productsUrl)
        var productList: ProductsList

        call?.enqueue(object : Callback<ProductsList?> {
            override fun onResponse(call: Call<ProductsList?>?, response: Response<ProductsList?>) {
                if(response.code() == 200) {
                    productList = response.body()!!
                    Log.d("API response", "Products retrieved")
                    Log.d("API response", "${response.raw()}")
                    val productObjects = ProductMapper.fromApiModel(productList, MAX_PRODUCTS)
                    holder.productsView.initRecyclerView(
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
                        ProductRecyclerAdapter(productObjects, context)
                    )
                } else {
                    // TODO: Show error fragment
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

    override fun getItemCount() = categoryList.size

    /**
     * ViewHolder for each categoryRV. Contains nested RecyclerView for product cards
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.category_row_title)
        private val showAllButton: ImageButton = view.findViewById(R.id.show_all_button)
        val productsView: RecyclerView = view.findViewById(R.id.child_products_rv)
        lateinit var categoryId: String
        lateinit var productsUrl: String

        init {
            // On click of show all button for each category, It redirects to page showing
            // all products of that category.
            showAllButton.setOnClickListener {
                val bundle = bundleOf(
                    CATEGORY_TITLE to textView.text,
                    CATEGORY_ID to categoryId,
                    "productsUrl" to productsUrl
                )
                view.findNavController()
                    .navigate(R.id.action_navigation_home_to_categoryFragment, bundle)
            }
        }
    }
}