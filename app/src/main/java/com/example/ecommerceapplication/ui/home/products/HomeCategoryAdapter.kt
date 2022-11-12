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
import com.example.domain.models.Category
import com.example.domain.models.Product
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.extensions.initRecyclerView
import com.example.ecommerceapplication.ui.home.HomeViewModel
import com.example.ecommerceapplication.ui.category.CategoryFragment.Companion.CATEGORY_OBJECT
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Adapter for recycler view to display products in home page
 * This RecyclerView contains another recycler view for horizontal product cards scroller
 * @param onItemClicked Onclick function to invoke on click single product
 */
class HomeCategoryAdapter(
    categories: List<Category>,
    val context: Context,
    private val viewModel: HomeViewModel,
    private val forceReload: Boolean,
    private val onItemClicked: (Product) -> Unit
) : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    private val categoryList: MutableList<Category> = categories.toMutableList()

    // Counts the number of categories removed (if api returns no products for that category)
    var emptyCategoryCount = 0

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
        val currCategory = categoryList[holder.absoluteAdapterPosition]
        holder.textView.text = currCategory.categoryTitle
        holder.category = currCategory
        holder.productsUrl = currCategory.productsUrl

        holder.productsLoader.startShimmerAnimation()
        if (absPosition >= 0 && categoryList.size > absPosition) {

            CoroutineScope(Dispatchers.Main).launch {
                val products = viewModel.getTopProductsForCategory(currCategory, forceReload)
                Log.d("Products", "Category: ${currCategory.categoryTitle} Products: ${products.getOrNull()}")
                initializeProducts(products.getOrNull(), holder, absPosition)
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    private fun initializeProducts(products: List<Product>?, holder: ViewHolder, position: Int) {
        if (products.isNullOrEmpty()) {
            // if no products are returned for that category
            categoryList.removeAt(position)
            emptyCategoryCount++
            Log.d("Product", "Product ${holder.category.categoryTitle}")
            notifyItemRemoved(position)
        } else {
            holder.productsLoader.stopShimmerAnimation()
            holder.productsLoader.visibility = View.GONE
            holder.productsView.visibility = View.VISIBLE

            holder.productsView.initRecyclerView(
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
                ProductRecyclerAdapter(products, context, onItemClicked)
            )
        }
    }

    override fun getItemCount() = categoryList.size

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
            // On click of show all button for each category, redirect to page showing
            // all products of that category.
            categoryTopBar.setOnClickListener {
                val bundle = bundleOf(CATEGORY_OBJECT to category)
                view.findNavController()
                    .navigate(R.id.action_navigation_home_to_categoryFragment, bundle)
            }
        }
    }
}