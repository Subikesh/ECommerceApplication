package com.example.ecommerceapplication.ui.home.products

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.getProductMapHome
import com.example.data.roomdb.entities.Category
import com.example.ecommerceapplication.R
import com.example.ecommerceapplication.extensions.initRecyclerView

/**
 * Adapter for recycler view to display products in home page
 * This RecyclerView contains another recycler view for horizontal product cards scroller
 * @param categoryList List of strings showing the category title
 */
class HomeCategoryAdapter(private val categoryList: List<Category>, val context: Context) :
    RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {

    companion object {
        /** Maximum products to be shown inside a category RV */
        const val MAX_PRODUCTS = 6
    }

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
        holder.textView.text = currCategory.title
        holder.categoryId = currCategory.categoryId
        holder.productsUrl = currCategory.productsUrl

        val productsArray =
            getProductMapHome(com.example.data.roomdb.entities.categoryList)[currCategory.categoryId]
        if (productsArray != null) {
            val productsAdapter =
                ProductRecyclerAdapter(productsArray.toTypedArray(), context)

            // Setting the RecyclerView of child products list
            holder.productsView.initRecyclerView(
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
                productsAdapter,
                true
            )
        }
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