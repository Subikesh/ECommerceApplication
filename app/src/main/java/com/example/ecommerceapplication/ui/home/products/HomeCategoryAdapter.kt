package com.example.ecommerceapplication.ui.home.products

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.api.GetApiDataService
import com.example.data.api.RetrofitInstance
import com.example.data.api.models.ProductsList
import com.example.data.repository.ProductApiMapperImpl
import com.example.domain.models.Category
import com.example.domain.models.Product
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.HomeViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Adapter for recycler view to display products in home page
 * This RecyclerView contains another recycler view for horizontal product cards scroller
 * @param onItemClicked Onclick function to invoke on click single product
 */
class HomeCategoryAdapter(
    val context: Context,
    private val viewModel: HomeViewModel,
    private val onItemClicked: (Product) -> Unit
) : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

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
        val absPosition = holder.absoluteAdapterPosition
        val currCategory = viewModel.categoryList!![holder.absoluteAdapterPosition].first
        holder.textView.text = currCategory.categoryTitle
        holder.category = currCategory
        holder.productsUrl = currCategory.productsUrl

        holder.productsLoader.startShimmerAnimation()
        if (absPosition >= 0) {
            val service = RetrofitInstance.retrofitInstance?.create(GetApiDataService::class.java)
            val call = service?.getProductsList(currCategory.productsUrl)
            var productList: ProductsList

            if (viewModel.categoryList!![absPosition].second != null) {
                initializeProducts(holder, absPosition)
            } else {
                call?.enqueue(object : Callback<ProductsList?> {
                    override fun onResponse(
                        call: Call<ProductsList?>?,
                        response: Response<ProductsList?>
                    ) {
                        if (response.body() != null) {
                            productList = response.body()!!
                            productList.setCategory(currCategory.categoryId)
                            val productObjects = ProductApiMapperImpl.fromApiModel(productList)

                            viewModel.categoryList!![absPosition].second = productObjects

                            initializeProducts(holder, absPosition)
                        } else {
                            Toast.makeText(context, "Products retrieval failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ProductsList?>?, t: Throwable) {
                        holder.productsLoader.visibility = View.GONE
                        Toast.makeText(context, "Product retrieval failed", Toast.LENGTH_SHORT)
                            .show()
                        Log.d("API response", "Failed products retrieval: $t")
                    }
                })
            }
        }
    }

    private fun initializeProducts(holder: ViewHolder, position: Int) {
        if (viewModel.categoryList!![position].second.isNullOrEmpty()) {
            viewModel.categoryList?.removeAt(position)
            notifyItemRemoved(position)
        } else {
            holder.productsLoader.stopShimmerAnimation()
            holder.productsLoader.visibility = View.GONE
            holder.productsView.visibility = View.VISIBLE

            holder.productsView.initRecyclerView(
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
                ProductRecyclerAdapter(
                    viewModel.categoryList!![position].second!!,
                    context,
                    onItemClicked
                )
            )
        }
    }

    override fun getItemCount() = viewModel.categoryList?.size ?: 0

    /**
     * ViewHolder for each categoryRV. Contains nested RecyclerView for product cards
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productsLoader: ShimmerFrameLayout = view.findViewById(R.id.products_shimmer)
        val textView: TextView = view.findViewById(R.id.category_row_title)
        private val categoryTopBar: LinearLayout = view.findViewById(R.id.category_row_topbar)
        val productsView: RecyclerView = view.findViewById(R.id.child_products_rv)
        lateinit var category: Category
        lateinit var productsUrl: String

        init {
            // On click of show all button for each category, It redirects to page showing
            // all products of that category.
            categoryTopBar.setOnClickListener {
                val bundle = bundleOf(CATEGORY_OBJECT to category)
                view.findNavController()
                    .navigate(R.id.action_navigation_home_to_categoryFragment, bundle)
            }
        }
    }
}